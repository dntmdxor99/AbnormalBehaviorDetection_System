import torch
import torch.nn as nn


class ResNet(nn.Module):
    """
    Single residual block of ResNet-50.
    """

    def __init__(self, inputDim, middleDim, outputDim, tempKernelSize, stride):
        super(ResNet, self).__init__()
        
        # Number of channels and spatial size of input should be matched with output
        # because of residual connection.
        if (inputDim != outputDim) or (stride > 1):
            self.projection = nn.Sequential(
                nn.Conv3d(inputDim, outputDim, kernel_size = (1, 1, 1), stride = (1, stride, stride), bias = False),
                nn.BatchNorm3d(outputDim)
            )

        self.stacks = nn.Sequential(
            nn.Conv3d(in_channels = inputDim, out_channels = middleDim, kernel_size = (tempKernelSize, 1, 1), padding = (tempKernelSize // 2, 0, 0), bias = False),
            nn.BatchNorm3d(middleDim),
            nn.ReLU(inplace = True),

            nn.Conv3d(in_channels = middleDim, out_channels =  middleDim, kernel_size = (1, 3, 3), stride = (1, stride, stride), padding = (0, 1, 1), bias = False),
            nn.BatchNorm3d(middleDim),
            nn.ReLU(inplace = True),

            nn.Conv3d(in_channels = middleDim, out_channels =  outputDim, kernel_size = (1, 1, 1), bias = False),
            nn.BatchNorm3d(outputDim),
            nn.ReLU(inplace = True)
        )

        self.relu = nn.ReLU(inplace = True)


    def forward(self, x):
        identity = x
        x = self.stacks(x)

        if hasattr(self, 'projection'):
            identity = self.projection(identity)

        x = x + identity
        x = self.relu(x)

        return x


