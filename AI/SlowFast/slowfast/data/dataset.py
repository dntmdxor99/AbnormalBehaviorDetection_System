import os
import cv2

import torch
from torch.utils.data import ConcatDataset, Dataset
import torch.nn.functional as F
from torchvision import transforms
from .utils import allTransform

class VideoDataset(Dataset):
    def __init__(self, opt):
        self.opt = opt
        self.keys = []

        self.frameNums = opt['T'] * opt['tau']
        self.tau = opt['tau']

        with open(opt['metaInfoFile'], 'r') as f:
            for line in f:
                folder, label = line.split(' ')

                dataLens = len(os.listdir(os.path.join(self.opt['trainDataPath'], folder)))
                cnt = dataLens // self.frameNums     ## 길이가 n이라면 n // 64개의 데이터를 만들 수 있음.

                for i in range(cnt):
                    self.keys.append([f'{folder}/{i * self.frameNums:08d}', label])
            
            
    def __len__(self):
        return len(self.keys)
    

    def __getitem__(self, idx):
        key = self.keys[idx]
        clipName, frameName = key[0].split('/')
        firstFrameNum = int(frameName)      # 맨 처음 프레임 번호
        label = int(key[1])
        fastData = []
        fastInternal = self.opt['tau'] // self.opt['alpha']
        
        for i in range(firstFrameNum, firstFrameNum + self.frameNums, fastInternal):
            image = cv2.imread(os.path.join(self.opt['trainDataPath'], clipName, f'{i:08d}.png'))
            fastData.append(image)

        slowData, fastData = allTransform(fastData, self.opt['alpha'])

        return slowData, fastData, label