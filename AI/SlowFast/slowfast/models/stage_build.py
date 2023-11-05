import torch
import torch.nn as nn


class Stage1(nn.Module):
    def __init__(self, opt):
        super(Stage1, self).__init__()

        self.slowStream = nn.Sequential(
            nn.Conv3d(in_channels = 3, out_channels = 64, kernel_size=(1, 7, 7), stride=(1, 2, 2), padding=(0, 3, 3), bias=False),
            nn.BatchNorm3d(num_features = 64),
            nn.ReLU(inplace = True),
            nn.MaxPool3d(kernel_size = (1, 3, 3), stride = (1, 2, 2), padding = (0, 1, 1))
        )

        self.fastStream = nn.Sequential(
            ## We will use RGB images for fast stream
            nn.Conv3d(in_channels = 3, out_channels = 64 // opt['beta'], kernel_size = (5, 7, 7), stride = (1, 2, 2), padding = (2, 3, 3), bias=False),
            nn.BatchNorm3d(num_features = 64 // opt['beta']),
            nn.ReLU(inplace = True),
            nn.MaxPool3d(kernel_size = (1, 3, 3), stride = (1, 2, 2), padding = (0, 1, 1))
        )


    def forward(self, slowInput, fastInput):
        return self.slowStream(slowInput), self.fastStream(fastInput)


class StageBuild(nn.Module):
    ## This Class is for creating stageBlocks that make up each stage.
    def __init__(self, opt, i, block):
        super(StageBuild, self).__init__()

        middleDim = opt['middleDim']
        outputDim = opt['outputDim']
        stageBlocks = opt['stageBlocks']
        beta = opt['beta']
        numStageBlocks = opt['stageBlocks'][i]

        inputDim = (outputDim[i - 1] + 2 * outputDim[i - 1] // beta, outputDim[i - 1] // beta)
        middleDim = (middleDim[i], middleDim[i] // beta)
        outputDim = (outputDim[i], outputDim[i] // beta)
        tempKernelSize = ((0, 0), (0, 0), (1, 3), (1, 3), (3, 3), (3, 3))[i]
        stride = (0, 0, 1, 2, 2, 2)[i]

        streams = [[] for _ in range(2)]
        for stream in range(2):
            for i in range(numStageBlocks):
                if i == 0:
                    streams[stream].append(block(inputDim[stream], middleDim[stream], outputDim[stream], tempKernelSize[stream], stride))
                else:
                    streams[stream].append(block(outputDim[stream], middleDim[stream], outputDim[stream], tempKernelSize[stream], 1))

        self.slowStream = nn.Sequential(*streams[0])
        self.fastStream = nn.Sequential(*streams[1])


    def forward(self, slowInput, fastInput):
        return self.slowStream(slowInput), self.fastStream(fastInput)