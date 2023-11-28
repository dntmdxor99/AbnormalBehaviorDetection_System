import os
import cv2

import torch
from torch.utils.data import ConcatDataset, Dataset
import torch.nn.functional as F
from torchvision import transforms
from torch.utils.data import DataLoader
from .utils import allTransform


def construct_loader(opt, split):
    if split == "train":
        shuffle = opt['shuffle']
        drop_last = True
    elif split == "val":
        shuffle = False
        drop_last = False
    else:
        raise NotImplementedError()

    batch_size_per_gpu = opt['batchSize']

    dataset = VideoDataset(opt, split)
    loader = DataLoader(
        dataset,
        batch_size=batch_size_per_gpu,
        shuffle=shuffle,
        num_workers=opt['numWorker'],
        pin_memory=True,
        drop_last=drop_last
    )
    return loader


class VideoDataset(Dataset):
    def __init__(self, opt, split):
        self.opt = opt
        self.keys = []

        self.frameNums = opt['T'] * opt['tau']
        self.tau = opt['tau']

        self.split = split

        with open(opt[f'{split}MetaInfoFile'], 'r') as f:
            for line in f:
                folder, label = line.split(' ')

                dataList = os.listdir(os.path.join(self.opt[f'{split}DataPath'], folder))
                # print(dataList)
                # firstFrameNum = int(min(dataList).split('.')[0])
                firstFrameNum = int(min(dataList).split('.')[0])
                dataLens = len(dataList)
                # dataLens = len(os.listdir(os.path.join(self.opt[f'{split}DataPath'], folder)))
                cnt = dataLens // self.frameNums     ## 길이가 n이라면 n // 32개의 데이터를 만들 수 있음.

                for i in range(cnt):
                    self.keys.append([f'{folder}/{firstFrameNum + i * self.frameNums:08d}', label.strip()])

            
    def __len__(self):
        return len(self.keys)
    

    def __getitem__(self, idx):
        key = self.keys[idx]
        clipName, frameName = key[0].split('/')
        # print(clipName, frameName)
        firstFrameNum = int(frameName)      # 맨 처음 프레임 번호
        label = int(key[1])
        fastData = []
        fastInternal = self.opt['tau'] // self.opt['alpha']
        
        for i in range(firstFrameNum, firstFrameNum + self.frameNums, fastInternal):
            image = cv2.imread(os.path.join(self.opt[f'{self.split}DataPath'], clipName, f'{i:08d}.png'))
            fastData.append(image)

        slowData, fastData = allTransform(fastData, self.frameNums // self.opt['tau'])

        return slowData, fastData, label