import torch
import math
import torch.nn.functional as F
import numpy as np
import cv2
from torchvision import transforms
import random


def toTensor(imgs, bgr2rgb=True, float32=True):
    def _totensor(img, bgr2rgb, float32):
        if img.shape[2] == 3 and bgr2rgb:
            if img.dtype == 'float64':
                img = img.astype('float32')
            img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img = torch.from_numpy(img.transpose(2, 0, 1))
        if float32:
            img = img.float()
        return img

    return [_totensor(img, bgr2rgb, float32) for img in imgs]


def toRandomResize(imgs, min = 256, max = 320):
    def _toRandomResize(img):
        newSize = int(round(np.random.uniform(min, max)))
        height = img.shape[0]
        width = img.shape[1]

        if width < height:
            newWidth = newSize
            newHeight = int(height * (float(newWidth) / width))
        else:
            new_height = newSize
            newWidth = int(width * (float(new_height) / height))

        img = cv2.resize(img, (newWidth, new_height))
        return img

    return [_toRandomResize(img) for img in imgs]


def toRandomCrop(imgs, size = 224):
    def _toRandomCrop(img):
        height = img.shape[0]
        heightOffset = np.random.randint(0, height - size) if height > size else 0

        width = img.shape[1]
        widthOffset = np.random.randint(0, width - size) if width > size else 0

        cropped_images = img[heightOffset : heightOffset + size, widthOffset : widthOffset + size, :]
        return cropped_images

    return [_toRandomCrop(img) for img in imgs]


def toRandomHorizontalFlip(imgs):
    def _toRandomHorizontalFlip(img):
        if random.random() > 0.5:
            cv2.flip(img, 1, img)

        return img

    return [_toRandomHorizontalFlip(img) for img in imgs]


def toNomalize(imgs):
    def _toNomalize(img):
        transforms.Normalize([0.45, 0.45, 0.45], [0.225, 0.225, 0.225], inplace = True)(img)

        return img

    return [_toNomalize(img) for img in imgs]


def toPair(imgs, alpha = 8):
    # slow_input = torch.index_select(imgs, 1, torch.linspace(0, imgs.shape[1] - 1, imgs.shape[1] // configs.alpha).long())
    fastData = imgs
    slowData = torch.index_select(imgs, 1, torch.linspace(0, len(imgs) - 1, alpha).long())

    return slowData, fastData


def allTransform(imgs, alpha):
    imgs = toRandomResize(imgs)
    imgs = toRandomCrop(imgs)
    imgs = toRandomHorizontalFlip(imgs)
    imgs = toTensor(imgs)
    imgs = toNomalize(imgs)
    imgs = torch.stack(imgs, dim = 0)
    imgs = imgs.transpose(0, 1)
    
    slowImgs, fastImgs = toPair(imgs, alpha)
    # print(len(imgs))
    return slowImgs, fastImgs