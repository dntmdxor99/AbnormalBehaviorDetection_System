import os
import cv2

import torch
from torch.utils.data import ConcatDataset, Dataset
import torch.nn.functional as F
from torchvision import transforms
from torch.utils.data import DataLoader
from .utils import allTransform

import numpy as np

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

        self.yoloInputSize = opt['inputSize']
        self.resize = transforms.Resize((640, 640))

        with open(opt[f'{split}MetaInfoFile'], 'r') as f:
            for line in f:
                folder, label = line.split(' ')
                dataList = os.listdir(os.path.join(self.opt[f'{split}DataPath'], folder))
                firstFrameNum = int(min(dataList).split('.')[0])
                dataLens = len(dataList)
                cnt = dataLens // self.frameNums     ## 길이가 n이라면 n // 32개의 데이터를 만들 수 있음.

                for i in range(cnt):
                    self.keys.append([f'{folder}/{firstFrameNum + i * self.frameNums:08d}', label.strip()])

            
    def __len__(self):
        return len(self.keys)
    

    def __getitem__(self, idx):
        key = self.keys[idx]
        clipName, frameName = key[0].split('/')
        firstFrameNum = int(frameName)      # 맨 처음 프레임 번호
        label = int(key[1])
        imagesOri = []
        images = []
        fastInternal = 1
        
        for i in range(firstFrameNum, firstFrameNum + self.frameNums, fastInternal):
            image = cv2.imread(os.path.join(self.opt[f'{self.split}DataPath'], clipName, f'{i:08d}.jpg'))

            
            imageOri = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            imageOri = imageOri.transpose((2, 0, 1))  # HWC to CHW, BGR to RGB
            imagesOri.append(imageOri)

            image = cv2.resize(image, (860, 540))
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            image = image.transpose((2, 0, 1))

            images.append(image)

        return np.array(images), np.array(imagesOri), label


def letterbox(im, new_shape=(640, 640), color=(114, 114, 114), auto=True, scaleFill=False, scaleup=True, stride=32):
    # Resize and pad image while meeting stride-multiple constraints
    shape = im.shape[:2]  # current shape [height, width]
    if isinstance(new_shape, int):
        new_shape = (new_shape, new_shape)

    # Scale ratio (new / old)
    r = min(new_shape[0] / shape[0], new_shape[1] / shape[1])
    if not scaleup:  # only scale down, do not scale up (for better val mAP)
        r = min(r, 1.0)

    # Compute padding
    ratio = r, r  # width, height ratios
    new_unpad = int(round(shape[1] * r)), int(round(shape[0] * r))
    dw, dh = new_shape[1] - new_unpad[0], new_shape[0] - new_unpad[1]  # wh padding
    if auto:  # minimum rectangle
        dw, dh = np.mod(dw, stride), np.mod(dh, stride)  # wh padding
    elif scaleFill:  # stretch
        dw, dh = 0.0, 0.0
        new_unpad = (new_shape[1], new_shape[0])
        ratio = new_shape[1] / shape[1], new_shape[0] / shape[0]  # width, height ratios

    dw /= 2  # divide padding into 2 sides
    dh /= 2

    if shape[::-1] != new_unpad:  # resize
        im = cv2.resize(im, new_unpad, interpolation=cv2.INTER_LINEAR)
    top, bottom = int(round(dh - 0.1)), int(round(dh + 0.1))
    left, right = int(round(dw - 0.1)), int(round(dw + 0.1))
    im = cv2.copyMakeBorder(im, top, bottom, left, right, cv2.BORDER_CONSTANT, value=color)  # add border
    return im