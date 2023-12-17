import os
import sys
from pathlib import Path

import torch
import torchvision.transforms.functional as F
from torchvision import transforms


FILE = Path(__file__).resolve()
ROOT = FILE.parents[0]  # YOLOv5 root directory
if str(ROOT) not in sys.path:
    sys.path.append(str(ROOT))  # add ROOT to PATH
ROOT = Path(os.path.relpath(ROOT, Path.cwd()))  # relative

from models.common import DetectMultiBackend
from utils.general import LOGGER, cv2, non_max_suppression, scale_boxes

from data.build_train_loader_wYolo import construct_loader
from utils.options import parseOptions


def run(opt, line_thickness=3):
    save_dir = f'{ROOT}/runs/detect/exp'

    # Load model
    device = opt['device']
    model = DetectMultiBackend(opt['yoloPretrained'], device=torch.device(device), dnn=False, fp16=False)
    trainLoader = construct_loader(opt, 'train')
    resize = transforms.Resize((224, 224))
    # Run inference

    with torch.no_grad():
        model.eval()
        for idx, (im, im0s, label) in enumerate(trainLoader):
            im = im.to(model.device)
            im = im.half() if model.fp16 else im.float()  # uint8 to fp16/32
            im /= 255  # 0 - 255 to 0.0 - 1.0
            if len(im.shape) == 3:
                im = im[None]  # expand for batch dim

            cropResizedTensor = torch.zeros((im.shape[0], im.shape[1], 3, 224, 224))

            for b in range(im.shape[0]):
                pred = model(im[b])
                pred = non_max_suppression(pred, opt['confThres'], opt['iouThres'], 0, False, max_det = opt['maxDet'])

                # Process predictions
                im0 = im0s[b]
                os.makedirs(f'{save_dir}{b}', exist_ok=True)

                for i, det in enumerate(pred):  # per image
                    if len(det):
                        # Rescale boxes from img_size to im0 size
                        # det[:, :4] = scale_boxes(im[0].shape[2:], det[:, :4], im0[0].shape).round()
                        det[:, :4] = scale_boxes(im[0].shape[2:], det[:, :4], (2160, 3840, 3)).round()

                        # Write results
                        x1 = int(det[:, 0].min())
                        y1 = int(det[:, 1].min())
                        x2 = int(det[:, 2].max())
                        y2 = int(det[:, 3].max())

                        # cv2.imwrite(f'{save_dir}{b}/{str(i).zfill(8)}.png', im0[i][y1:y2, x1:x2])
                        # print(im0[i].shape)
                        # print(x1, y1, x2, y2)
                        # print(im0[i][:, y1:y2, x1:x2].shape)
                        cropResizedTensor[b][i] = resize(im0[i][:, y1:y2, x1:x2])
                    elif i:
                        cropResizedTensor[b][i] = resize(im0[i][:, y1:y2, x1:x2])
                    else:
                        cropResizedTensor[b][i] = resize(im0[i])


        for i in range(cropResizedTensor.shape[0]):
            for j in range(cropResizedTensor.shape[1]):
                cv2.imwrite(f'{save_dir}{i}/{str(j).zfill(8)}.png', cv2.cvtColor(cropResizedTensor[i][j].numpy().transpose(1, 2, 0), cv2.COLOR_RGB2BGR))


if __name__ == '__main__':
    opt = parseOptions()
    run(opt)