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


FILE = Path(__file__).resolve()
ROOT = FILE.parents[0]  # YOLOv5 root directory
if str(ROOT) not in sys.path:
    sys.path.append(str(ROOT))  # add ROOT to PATH
ROOT = Path(os.path.relpath(ROOT, Path.cwd()))  # relative


def yoloInference(opt, yolo, inputs, originals, label, device):
    resize = transforms.Resize((224, 224))
    normalize = transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225], inplace = True)
    frameNums = opt['T'] * opt['tau']
    tau = opt['tau']
    batchSize = opt['batchSize']
    fastInternal = opt['tau'] // opt['alpha']

    fastData = torch.zeros((batchSize, frameNums // fastInternal, 3, 224, 224), dtype = torch.float32).to(device)
    xyxys = [[0] * (frameNums // fastInternal) for _ in range(batchSize)]
                # slowData = torch.zeros((batchSize, frameNums // tau, 3, 224, 224), dtype = torch.float32).to(device)
    with torch.no_grad():
        inputs = inputs.to(yolo.device)
        inputs = inputs.half() if yolo.fp16 else inputs.float()  # uint8 to fp16/32
        inputs /= 255  # 0 - 255 to 0.0 - 1.0
        if len(inputs.shape) == 3:
            inputs = inputs[None]  # expand for batch dim

        for b in range(inputs.shape[0]):
            pred = yolo(inputs[b])
            pred = non_max_suppression(pred, opt['confThres'], opt['iouThres'], 0, False, max_det = opt['maxDet'])

            # Process predictions
            original = originals[b]
            for i, det in enumerate(pred):  # per image
                if len(det):
                    # Rescale boxes from img_size to im0 size
                    det[:, :4] = scale_boxes(inputs[0].shape[2:], det[:, :4], originals[b].shape[2:]).round()

                    # Write results
                    x1 = int(det[:, 0].min())
                    y1 = int(det[:, 1].min())
                    x2 = int(det[:, 2].max())
                    y2 = int(det[:, 3].max())
                    xyxys[b][i] = [x1, y1, x2, y2]

                    temp = resize(original[i][:, y1:y2, x1:x2]).float() / 255.0
                    fastData[b][i] = normalize(temp)
                elif i:
                    temp = resize(original[i][:, y1:y2, x1:x2]).float() / 255.0
                    fastData[b][i] = normalize(temp)
                else:
                    temp = resize(original[i]).float() / 255.0
                    fastData[b][i] = normalize(temp)

    slowData = torch.index_select(fastData, 1, torch.linspace(0, len(fastData) - 1, frameNums // tau).long()).to(device)
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
    model.load_state_dict(torch.load(opt['pretrainedModelPath'], map_location=device))
    model.eval()

    yolo = DetectMultiBackend(opt['yoloPretrained'], device=torch.device(device), dnn=False, fp16=False)
    yolo.eval()

    valLoader = construct_loader(opt, 'test')

    currentTime = datetime.now().strftime('%Y%m%d%H%M%S')
    savePath = os.path.join('./results', currentTime)
    os.makedirs(savePath, exist_ok=True)

    data_size = 0
    sum_top1_correct = 0
    sum_top5_correct = 0

    with tqdm(total = len(valLoader), desc = 'testing') as pbar:
        with torch.no_grad():
            for idx, (inputs, originals, label), folderName in enumerate(valLoader):
                slowData, fastData, label, xyxys = yoloInference(opt, yolo, inputs, originals, label, device)
                # Transfer the data to the current GPU device.
                # if opt['numGpus'] > 0:
                    # for i in range(len(inputs)):
                        # inputs[i] = inputs[i].cuda(non_blocking=True)
                    # slowData = slowData.cuda(non_blocking=True)
                    # fastData = fastData.cuda(non_blocking=True)

                label = torch.Tensor(label)
                preds = model(slowData, fastData)
                input_box_label(originals, preds, xyxys, savePath, folderName)
                top1_correct, top3_correct = num_topK_correct(preds, label, (1, 3))

                # Evaluating stats
                sum_top1_correct += top1_correct
                sum_top5_correct += top3_correct
                data_size += len(label)

        pbar.update()

        print(f'top1 acc: {sum_top1_correct / data_size * 100: .4f}%, '
            f'top4 acc: {sum_top5_correct / data_size * 100: .4f}%', flush=True)
        
if __name__ == "__main__":
    testPipeline()