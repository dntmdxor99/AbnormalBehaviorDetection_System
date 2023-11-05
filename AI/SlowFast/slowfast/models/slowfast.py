import torch
import torch.nn as nn
from .resnet import ResNet
from .classification import Classification
from .stage_build import StageBuild, Stage1


class Fuse(nn.Module):
    ## We will use Time-strided convolution to fuse the two streams
    def __init__(self, opt, inputDim):
        super(Fuse, self).__init__()

        self.fuse = nn.Sequential(
            nn.Conv3d(inputDim, inputDim * 2, kernel_size=(5, 1, 1), stride=(opt['alpha'], 1, 1), padding=(2, 0, 0), bias=False),
            nn.BatchNorm3d(inputDim * 2),
            nn.ReLU(inplace=True)
        )


    def forward(self, slowInput, fastInput):
        fastToSlow = self.fuse(fastInput)
        slowInput = torch.cat([slowInput, fastToSlow], 1)   # Channel-wise concat
        return slowInput, fastInput


class SlowFast(nn.Module):
    def __init__(self, opt):
        super(SlowFast, self).__init__()
        backbone = opt['backbone']
        middleDim = opt[backbone]['middleDim']
        outputDim = opt[backbone]['outputDim']
        stageBlocks = opt[backbone]['stageBlocks']
        beta = opt['beta']
        block = ResNet

        self.stages = [Stage1(opt), Fuse(opt, outputDim[1] // beta)]

        for i in range(2, 5):
            self.stages.append(StageBuild(opt, i, block))
            self.stages.append(Fuse(opt, outputDim[i] // beta))
        self.stages.append(StageBuild(opt, 5, block))
        self.classification = Classification(opt)
        # self.stage1 = Stage1(opt)
        # self.stage1_fuse = Fuse(opt, outputDim[1] // beta)
        # self.stage2 = StageBuild(opt, 2, block)
        # self.stage2_fuse = Fuse(opt, outputDim[2] // beta)
        # self.stage3 = StageBuild(opt, 3, block)
        # self.stage3_fuse = Fuse(opt, outputDim[3] // beta)
        # self.stage4 = StageBuild(opt, 4, block)
        # self.stage4_fuse = Fuse(opt, outputDim[4] // beta)
        # self.stage5 = StageBuild(opt, 5, block)
        # self.classification = Classification(opt)


    def forward(self, slowInput, fastInput):
        # slowInput, fastInput = self.stage1(slowInput, fastInput)
        # slowInput, fastInput = self.stage1_fuse(slowInput, fastInput)
        # slowInput, fastInput = self.stage2(slowInput, fastInput)
        # slowInput, fastInput = self.stage2_fuse(slowInput, fastInput)
        # slowInput, fastInput = self.stage3(slowInput, fastInput)
        # slowInput, fastInput = self.stage3_fuse(slowInput, fastInput)
        # slowInput, fastInput = self.stage4(slowInput, fastInput)
        # slowInput, fastInput = self.stage4_fuse(slowInput, fastInput)
        # slowInput, fastInput = self.stage5(slowInput, fastInput)
        # x = self.classification(slowInput, fastInput)

        for net in self.stages:
            slowInput, fastInput = net(slowInput, fastInput)
        x = self.classification(slowInput, fastInput)
        
        return x