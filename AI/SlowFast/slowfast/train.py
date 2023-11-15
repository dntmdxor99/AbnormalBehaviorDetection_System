import torch
from torchsummary import summary 
from torch.utils.data import DataLoader
import torch.nn.functional as F

from models.model_build import modelBuild
from utils.options import parseOptions
from data.dataset import VideoDataset

from tqdm import tqdm


def trainPipeline():
    opt = parseOptions()
    device = opt['device']
    model = modelBuild(opt).to(device)

    optimizer = torch.optim.SGD(model.parameters(), lr=0.01, momentum=0.9, weight_decay=0.0001, nesterov=True)
    schduler = torch.optim.lr_scheduler.CosineAnnealingLR(optimizer, T_max=opt['iterations'], eta_min=0.001)
    lossFunction = torch.nn.CrossEntropyLoss()

    dataSet = VideoDataset(opt)
    dataLoader = DataLoader(dataSet, batch_size = opt['batchSize'], shuffle = opt['shuffle'], num_workers = opt['numWorker'], pin_memory=True, drop_last=True)

    epochs = opt['iterations']  // len(dataLoader)

    for epoch in range(epochs):
        ## train
        model.train()

        sumLoss = 0
        sumTop1Correct = 0
        sumTop5Correct = 0

        with tqdm(total = len(dataLoader), desc=f'Epoch {epoch}, training') as pbar:
            for i, (slowData, fastData, label) in enumerate(dataLoader):
                if opt['device'] == 'cuda':
                    slowData = slowData.to(device, non_blocking = True)
                    fastData = fastData.to(device, non_blocking = True)
                    label = label.to(device, non_blocking = True)

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


        print(f'loss : {sumLoss / len(dataLoader): .4f}, lr : {optimizer.param_groups[0]["lr"]}')
        schduler.step()


if __name__ == "__main__":
    trainPipeline()