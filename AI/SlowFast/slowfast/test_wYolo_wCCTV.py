import os
import sys
import cv2

import torch
import torch.nn.functional as F
from torchvision import transforms

from models.model_build import modelBuild
from utils.options import parseOptions
from utils.input_box_label import input_box_label


from pathlib import Path
from models.common import DetectMultiBackend
from utils.general import non_max_suppression, scale_boxes

from data.build_test_loader_wYolo import construct_loader
from data.utils import allTransform

from tqdm import tqdm
from datetime import datetime
from time import sleep

from threading import Thread

from socket import *
import sys
from collections import deque
import time
import requests
import cv2
import base64


FILE = Path(__file__).resolve()
ROOT = FILE.parents[0]  # YOLOv5 root directory
if str(ROOT) not in sys.path:
    sys.path.append(str(ROOT))  # add ROOT to PATH
ROOT = Path(os.path.relpath(ROOT, Path.cwd()))  # relative


class RTSPVideoWriterObject(object):
    def __init__(self, src=0):
        # Create a VideoCapture object
        self.capture = cv2.VideoCapture(src)

        self.i = 0

        # Start the thread to read frames from the video stream
        # self.thread = Thread(target=self.update, args=())
        self.thread = Thread(target=self.getDataset, args=())
        self.thread.daemon = True
        self.thread.start()

    
    def createThread(self):
        self.thread = Thread(target=self.getDataset, args=())
        self.thread.daemon = True
        self.thread.start()


    def update(self):
        # Read the next frame from the stream in a different thread
        while True:
            if self.capture.isOpened():
                (self.status, self.frame) = self.capture.read()


    def getDataset(self):
        imagesOri = []
        images = []
        for i in range(32):
            if self.capture.isOpened():
                (self.status, self.frame) = self.capture.read()
            imageOri = cv2.cvtColor(self.frame, cv2.COLOR_BGR2RGB)
            imageOri = imageOri.transpose((2, 0, 1))
            imagesOri.append(torch.Tensor(imageOri))


            image = cv2.resize(self.frame, (860, 540))
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            image = image.transpose((2, 0, 1))
            images.append(torch.Tensor(image))

            if i == 15:
                self.byte64 = self.frame


        imagesOri = torch.stack(imagesOri, 0)
        images = torch.stack(images, 0)
        
        imagesOri.unsqueeze_(0)
        images.unsqueeze_(0)

        self.imagesOri = images
        self.images = images
        self.label = torch.Tensor([4])
        self.folderName = 'test'
        self.i += 1

        self.createThread()


def yoloInference(opt, yolo, inputs, originals, label, device):
    resize = transforms.Resize((224, 224))
    normalize = transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225], inplace = True)
    frameNums = opt['T'] * opt['tau']
    tau = opt['tau']
    batchSize = opt['batchSize']
    fastInternal = opt['tau'] // opt['alpha']

    fastData = torch.zeros((batchSize, frameNums // fastInternal, 3, 224, 224), dtype = torch.float32).to(device)
    xyxys = [0] * batchSize
    # xyxys = [[0] * frameNums for _ in range(batchSize)]

    with torch.no_grad():
        inputs = inputs.to(yolo.device)
        inputs = inputs.half() if yolo.fp16 else inputs.float()  # uint8 to fp16/32
        inputs /= 255  # 0 - 255 to 0.0 - 1.0
        if len(inputs.shape) == 3:
            inputs = inputs[None]  # expand for batch dim

        temp = inputs[:, frameNums // 2, :, :, :]
        pred = yolo(temp)
        pred = non_max_suppression(pred, opt['confThres'], opt['iouThres'], 0, False, max_det = opt['maxDet'])

        # for b in range(inputs.shape[0]):
        #     pred = yolo(inputs[b])
        #     pred = non_max_suppression(pred, opt['confThres'], opt['iouThres'], 0, False, max_det = opt['maxDet'])

            # Process predictions
            # original = originals[b]
        for i, det in enumerate(pred):  # per image
            if len(det):
                det[:, :4] = scale_boxes(inputs[0].shape[2:], det[:, :4], originals[0].shape[2:]).round()

                x1 = int(det[:, 0].min()) - 50
                y1 = int(det[:, 1].min()) - 50
                x2 = int(det[:, 2].max()) + 50
                y2 = int(det[:, 3].max()) + 50


                xyxys[i] = [x1, y1, x2, y2]

                try:
                    temp = resize(originals[i][:, :, y1:y2, x1:x2]).float() / 255.0
                    fastData[i] = normalize(temp)
                except:
                    temp = resize(originals[i]).float() / 255.0
                    fastData[i] = normalize(temp)
            else:
                temp = resize(originals[i]).float() / 255.0
                fastData[i] = normalize(temp)
        

    slowData = torch.index_select(fastData, 1, torch.linspace(0, len(fastData) - 1, frameNums // tau).long().to(device))
    slowData = slowData.to(device)
    slowData = slowData.to(torch.float32)
    fastData = fastData.transpose(1, 2)
    slowData = slowData.transpose(1, 2)
    label = label.to(device)

    return slowData, fastData, label, xyxys


def num_topK_correct(preds, labels, k_list=(0, 3)):
    _, top_k_indices = torch.topk(preds, max(k_list))
    expanded_labels = labels.view(-1, 1).expand_as(top_k_indices)
    match_matrix = ((expanded_labels - top_k_indices) == 0)
    num_correct = [match_matrix[:, :k].sum() for k in k_list]
    return num_correct


def testPipeline():
    opt = parseOptions()
    device = opt['device']

    model = modelBuild(opt).to(device)
    model.load_state_dict(torch.load(opt['pretrainedModelPath']))
    model.eval()

    yolo = DetectMultiBackend(opt['yoloPretrained'], device=torch.device(device), dnn=False, fp16=False)
    yolo.eval()

    rtsp_stream_link = opt['cctvRoute']
    video_stream_widget = RTSPVideoWriterObject(rtsp_stream_link)

    currentTime = datetime.now().strftime('%Y%m%d%H%M%S')
    savePath = os.path.join('./results', currentTime)
    os.makedirs(savePath, exist_ok=True)

    dq = deque()
    actionProb = [0, 0, 0, 0, 0, 0]
    actionCnt = [0, 0, 0, 0, 0, 0]

    metaData = {
        "metaDataId" : None,
        "foundTime" : "",
        "entityFoundTime" : "",
        "cctvId" : 1,
        "type" : "PERSON",
        "abnormalType" : "1",
        "quality" : "",
        "videoId" : 2,
        "photoId" : 1,
        "base64Image"  : None
    }

    # with tqdm(total = len(testLoader), desc = 'testing') as pbar:
    with torch.no_grad():
        while True:
            video_stream_widget.thread.join()
            try:
                inputs = video_stream_widget.images
                originals = video_stream_widget.imagesOri
                label = video_stream_widget.label
                folderName = video_stream_widget.folderName
            except:
                sleep(1)
                continue

            slowData, fastData, label, xyxys = yoloInference(opt, yolo, inputs, originals, label, device)
            label = torch.Tensor(label)
            preds = model(slowData, fastData)
            prob = F.softmax(preds, dim=1)
            

            top1Prob, top1Index = torch.topk(prob, 1)
            quality = round(top1Prob[0][0].item(), 2)
            actionType = top1Index[0][0].item()
            print(f'quality : {quality}, type : {actionType}')

            if len(dq) == 10:
                popType, popQuality = dq.popleft()
                actionProb[popType] -= popQuality
                actionCnt[popType] -= 1

            dq.append((actionType, quality))
            actionProb[actionType] += quality
            actionCnt[actionType] += 1

            if actionProb[actionType] >= 2.0:
                metaData["abnormalType"] = actionType
                temp = actionProb[actionType] / actionCnt[actionType]
                if temp > 0.7:
                    metaData["quality"] = "HIGH"
                elif temp > 0.5:
                    metaData["quality"] = "MIDDLE"
                else:
                    metaData["quality"] = "LOW"
                metaData["foundTime"] = time.strftime('%Y-%m-%dT%H:%M:%S')
                metaData["entityFoundTime"] = time.strftime('%Y-%m-%dT%H:%M:%S')

                image_encode = cv2.imencode('.jpg', video_stream_widget.byte64)
                b64_string = base64.b64encode(image_encode[1]).decode('utf-8')
                metaData["base64Image"] = b64_string

                s = requests.Session()
                s.post(url = "http://118.45.212.161:8080/metadata/create", json = metaData)

                dq.clear()
                actionProb = [0, 0, 0, 0, 0, 0]
                actionCnt = [0, 0, 0, 0, 0, 0]

            # pbar.update()
        
if __name__ == "__main__":
    testPipeline()