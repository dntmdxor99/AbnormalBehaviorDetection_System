import os
import cv2
import torch
import numpy as np

label = {'normal' : 0, 'fight' : 1, 'datefight' :1, 'assault' : 2, 'drunken' : 3, 'swoon' : 4, 'kidnap' : 5}
reverseLabel = {0 : 'normal', 1 : 'fight', 2 : 'assault', 3 : 'drunken', 4 : 'swoon', 5 :'kidnap'}

def input_box_label(images, preds, xyxys, savePath = None, folderName = None, intLabel = None):
    if folderName:
        folderName, frameNumber = folderName[0].split('/')
        frameNumber = int(frameNumber)
    if savePath:
        os.makedirs(f'{savePath}/{folderName}', exist_ok=True)

    images = images.cpu().numpy()
    images = images.transpose(0, 1, 3, 4, 2)
    batchSize = images.shape[0]
    frameNums = images.shape[1]
    if intLabel:
        idx = intLabel
        prob = float(preds[0][idx])
    else:
        probs, idx = torch.topk(preds, 1)
    color = (0, 0, 255)
    line_thickness = 3
    
    for i in range(batchSize):
        if intLabel:
            label = reverseLabel[intLabel]
        else:
            label = reverseLabel[int(idx[i][0])]
            prob = float(probs[i][0])
        if xyxys[i]:
            x1, y1, x2, y2 = xyxys[i]
        else:
            continue
        for j in range(frameNums):
            # if xyxys[i][j]:
            #     x1, y1, x2, y2 = xyxys[i][j]
            # else:
            #     continue
            image = images[i][j]
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            image = cv2.rectangle(image, (x1, y1), (x2, y2), color, line_thickness)
            if prob > 0.3:
                image = cv2.putText(image, f'{label} : {prob:.4f}', (x1, y1 - 10), 0, 1, color, line_thickness, cv2.LINE_AA, bottomLeftOrigin=False)
            else:
                image = cv2.putText(image, f'normal : {float(preds[0][0]):.4f}', (x1, y1 - 10), 0, 1, color, line_thickness, cv2.LINE_AA, bottomLeftOrigin=False)

            # cv2.imwrite(f'/Users/woo/Desktop/woo/project/AbnormalBehaviorDetection_System/AI/SlowFast/pretrained/slowfast/visualize/{i}_{j}.png', image)
            if savePath:
                cv2.imwrite(os.path.join(f'{savePath}/{folderName}', f'{str(frameNumber + j).zfill(8)}.png'), image)
            else:
                cv2.imshow('test', image)
                cv2.waitKey(0)
                cv2.destroyAllWindows()
            