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


from data.build_train_loader_wYolo import construct_loader

from tqdm import tqdm
from datetime import datetime

import time


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
    xyxys = [0] * batchSize
    with torch.no_grad():
        inputs = inputs.to(yolo.device)
        inputs = inputs.half() if yolo.fp16 else inputs.float()  # uint8 to fp16/32
        inputs /= 255  # 0 - 255 to 0.0 - 1.0
        if len(inputs.shape) == 3:
            inputs = inputs[None]  # expand for batch dim

        temp = inputs[:, frameNums // 2, :, :, :]
        pred = yolo(temp)
        pred = non_max_suppression(pred, opt['confThres'], opt['iouThres'], 0, False, max_det = opt['maxDet'])

        for i, det in enumerate(pred):  # per image
            if len(det):
                det[:, :4] = scale_boxes(inputs[0].shape[2:], det[:, :4], originals[0].shape[2:]).round()

                # Write results
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


def num_topK_correct(preds, labels, k_list=(1, 3)):
    _, top_k_indices = torch.topk(preds, max(k_list))
    expanded_labels = labels.view(-1, 1).expand_as(top_k_indices)
    match_matrix = ((expanded_labels - top_k_indices) == 0)
    num_correct = [match_matrix[:, :k].sum() for k in k_list]
    return num_correct


def trainPipeline():
    opt = parseOptions()
    device = opt['device']
    model = modelBuild(opt).to(device)
    model.load_state_dict(torch.load(opt['pretrainedPath']))

    yolo = DetectMultiBackend(opt['yoloPretrained'], device=torch.device(device), dnn=False, fp16=False)
    yolo.eval()

    # optimizer = torch.optim.SGD(model.parameters(), lr=0.01, momentum=0.9, weight_decay=0.0001, nesterov=True)
    optimizer = torch.optim.Adam(model.parameters(), lr=opt['lr'])
    # schduler = torch.optim.lr_scheduler.CosineAnnealingLR(optimizer, T_max=opt['iterations'], eta_min=0.001)
    scheduler = torch.optim.lr_scheduler.CosineAnnealingWarmRestarts(optimizer, T_0 = opt['iterations'] // 10, T_mult = 2, eta_min=0.001)
    lossFunction = torch.nn.CrossEntropyLoss()

    trainLoader = construct_loader(opt, 'train')
    valLoader = construct_loader(opt, 'val')

    epochs = opt['iterations']  // len(trainLoader)
    print(f'epochs : {epochs}, iterations : {opt["iterations"]}, epoch per iteration : {len(trainLoader)}, trainDataset Count : {len(trainLoader) * 32}')

    currentTime = datetime.now().strftime('%Y%m%d%H%M%S')
    savePath = os.path.join('./pretrained', currentTime)
    os.makedirs(savePath, exist_ok=True)
    top1Epoch = 0
    bestTop1Acc = 0
    top3Epoch = 0
    bestTop3Acc = 0

    totalStartTime = time.time()

    learningRate = []
    i = 0

    for epoch in range(epochs):
        print(f'epoch : {epoch}')
        # train
        model.train()
        sumLoss = 0
        trainingStartTime = time.time()

        # with tqdm(total = len(trainLoader), desc=f'Epoch {epoch}, training') as pbar:
            # for i, (slowData, fastData, label) in enumerate(trainLoader):
        for inputs, originals, label in trainLoader:
            i += 1
            learningRate.append((i, optimizer.param_groups[0]["lr"]))

            slowData, fastData, label, _ = yoloInference(opt, yolo, inputs, originals, label, device)

            label = F.one_hot(torch.Tensor(label), num_classes = opt['numClasses'])
            label = label.type(torch.float32)

            optimizer.zero_grad()

            preds = model(slowData, fastData)
            loss = lossFunction(preds, label)
            sumLoss += loss

            loss.backward()
            optimizer.step( )
            
            scheduler.step()

                # pbar.update()

        trainingEndTime = time.time()

        print(f'[Train] total loss : {sumLoss:.4f}, avgloss : {sumLoss / len(trainLoader): .4f}, lr : {optimizer.param_groups[0]["lr"]}, training time : {trainingEndTime - trainingStartTime:.2f} sec')
        with open(f'{savePath}/loss.txt', 'a') as f:
            f.write(f'{epoch} {sumLoss / len(trainLoader)}\n')
        with open(f'{savePath}/lr.txt', 'a') as f:
            for i, lr in learningRate:
                f.write(f'{i} {lr}\n')
        learningRate.clear()

        model.eval()

        data_size = 0
        sum_top1_correct = 0
        sum_top3_correct = 0
        evalStartTime = time.time()

        # with tqdm(total = len(valLoader), desc=f'Epoch {epoch}, evaluating') as pbar:
        with torch.no_grad():
            for inputs, originals, label in valLoader:
                slowData, fastData, label, xyxys = yoloInference(opt, yolo, inputs, originals, label, device)

                label = torch.Tensor(label)
                preds = model(slowData, fastData)
                top1_correct, top3_correct = num_topK_correct(preds, label, (1, 3))

                sum_top1_correct += top1_correct
                sum_top3_correct += top3_correct
                data_size += len(label)

                # pbar.update()

            top1_acc = sum_top1_correct / data_size * 100
            top3_acc = sum_top3_correct / data_size * 100

            if top1_acc > bestTop1Acc:
                top1Epoch = epoch
                bestTop1Acc = top1_acc
                torch.save(model.state_dict(), os.path.join(savePath, f'top1.pth'))

            if top3_acc > bestTop3Acc:
                top3Epoch = epoch
                bestTop3Acc = top3_acc
                torch.save(model.state_dict(), os.path.join(savePath, f'top3.pth'))

            evalEndTime = time.time()
            print(f'[Eval] top1 acc: {top1_acc: .2f}%, '
                f'top3 acc: {top3_acc: .2f}%, '
                f'best top 1 epoch: {top1Epoch}, '
                f'best top 3 epoch: {top3Epoch}, '
                f'evaluating time : {evalEndTime - evalStartTime:.2f} sec'
                , flush=True)
            
            print()

            with open(f'{savePath}/top1.txt', 'a') as f:
                f.write(f'{epoch} {top1_acc}\n')
            with open(f'{savePath}/top3.txt', 'a') as f:
                f.write(f'{epoch} {top3_acc}\n')
        
        if epoch % opt['storeNumber'] == 0 or (epoch == epochs - 1):
            torch.save(model.state_dict(), os.path.join(savePath, f'epoch_{epoch}.pth'))

        torch.cuda.empty_cache()

    totalEndTime = time.time()
    print(f'best top 1 epoch: {top1Epoch}, '
          f'best top 1 acc: {bestTop1Acc: .2f}%, '
        f'best top 5 epoch: {top3Epoch}, '
        f'best top 5 acc: {bestTop3Acc: .2f}%, '
        f'training time : {totalEndTime - totalStartTime:.2f} sec')
        
if __name__ == "__main__":
    trainPipeline()