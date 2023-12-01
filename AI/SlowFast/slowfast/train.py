import os
import sys

import torch
# from torchsummary import summary 

import torch.nn.functional as F

from models.model_build import modelBuild
from utils.options import parseOptions
# from data.dataset import VideoDataset
# from data.build_loader import construct_loader
from data.build_train_loader import construct_loader

from tqdm import tqdm

from datetime import datetime


def num_topK_correct(preds, labels, k_list=(0, 3)):
    _, top_k_indices = torch.topk(preds, max(k_list))
    expanded_labels = labels.view(-1, 1).expand_as(top_k_indices)
    match_matrix = ((expanded_labels - top_k_indices) == 0)
    num_correct = [match_matrix[:, :k].sum() for k in k_list]
    return num_correct


def trainPipeline():
    opt = parseOptions()
    device = opt['device']
    model = modelBuild(opt)
    model = model.to(device)

    optimizer = torch.optim.SGD(model.parameters(), lr=0.01, momentum=0.9, weight_decay=0.0001, nesterov=True)
    schduler = torch.optim.lr_scheduler.CosineAnnealingLR(optimizer, T_max=opt['iterations'], eta_min=0.001)
    lossFunction = torch.nn.CrossEntropyLoss()

    # dataSet = VideoDataset(opt)
    # dataLoader = DataLoader(dataSet, batch_size = opt['batchSize'], shuffle = opt['shuffle'], num_workers = opt['numWorker'], pin_memory=True, drop_last=True)
    trainLoader = construct_loader(opt, 'train')
    valLoader = construct_loader(opt, 'val')

    epochs = opt['iterations']  // len(trainLoader.dataset)
    print(f'epochs : {epochs}, iterations : {opt["iterations"]}, len(trainDataset) : {len(trainLoader.dataset)}')

    currentTime = datetime.now().strftime('%Y%m%d%H%M%S')
    savePath = os.path.join('./pretrained', currentTime)
    os.makedirs(savePath, exist_ok=True)

    for epoch in range(epochs):
        ## train
        model.train()

        sumLoss = 0
        sumTop1Correct = 0
        sumTop5Correct = 0

        with tqdm(total = len(trainLoader), desc=f'Epoch {epoch}, training') as pbar:
            for i, (slowData, fastData, label) in enumerate(trainLoader):
                if opt['device'] == 'cuda':
                    slowData = slowData.to(device)
                    fastData = fastData.to(device)
                    label = label.to(device)

                label = torch.Tensor(label)
                optimizer.zero_grad()
                optimizer.param_groups[0]['lr'] = schduler.get_lr()[0]

                preds = model(slowData, fastData)
                loss = lossFunction(preds, label)
                sumLoss += loss

                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

                pbar.update()


        print(f'loss : {sumLoss / len(trainLoader): .4f}, lr : {optimizer.param_groups[0]["lr"]}')
        schduler.step()


        model.eval()

        data_size = 0
        sum_top1_correct = 0
        sum_top5_correct = 0

        with tqdm(total = len(valLoader), desc=f'Epoch {epoch}, evaluating') as pbar:
            with torch.no_grad():
                for i, (slowData, fastData, label) in enumerate(valLoader):
                    # Transfer the data to the current GPU device.
                    if opt['numGpus'] > 0:
                        # for i in range(len(inputs)):
                            # inputs[i] = inputs[i].cuda(non_blocking=True)
                        slowData = slowData.cuda(non_blocking=True)
                        fastData = fastData.cuda(non_blocking=True)
                        label = label.cuda(non_blocking=True)

                    label = torch.Tensor(label)
                    preds = model(slowData, fastData)
                    top1_correct, top3_correct = num_topK_correct(preds, label, (1, 3))

                    # Evaluating stats
                    sum_top1_correct += top1_correct
                    sum_top5_correct += top3_correct
                    data_size += len(label)

            pbar.update()

            print(f'top1 acc: {sum_top1_correct / data_size * 100: .4f}%, '
                f'top4 acc: {sum_top5_correct / data_size * 100: .4f}%', flush=True)
        
        torch.save(model.state_dict(), os.path.join(savePath, f'epoch_{epoch}.pth'))
        
if __name__ == "__main__":
    trainPipeline()