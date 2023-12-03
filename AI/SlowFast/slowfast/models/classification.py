import torch
import torch.nn as nn

class Classification(nn.Module):
    ## Last part of SlowFast Model, for ClassificationSlowFast
    def __init__(self, opt, dropoutRate = 0.5):      ## in the paper, default dropoutRate = 0.5
        super(Classification, self).__init__()

        T = opt['T']
        alpha = opt['alpha']
        beta = opt['beta']
        outputDim = opt['outputDim']
        numClasses = opt['numClasses']

        self.slowStreamEnd = nn.AvgPool3d((T, 7, 7))        # this function is  Global Average Pooling 3D
        self.fastStreamEnd = nn.AvgPool3d((alpha * T, 7, 7))

        if dropoutRate > 0:
            self.dropout = nn.Dropout(dropoutRate)

        self.fc = nn.Linear(outputDim[-1] + outputDim[-1] // beta, numClasses)
        self.softmax = nn.Softmax(dim=1)


    def forward(self, slowInput, fastInput):
        slowInput = self.slowStreamEnd(slowInput)
        slowInput = slowInput.view(slowInput.shape[0], -1)

        fastInput = self.fastStreamEnd(fastInput)
        fastInput = fastInput.view(fastInput.shape[0], -1)

        x = torch.cat([slowInput, fastInput], 1)

        if hasattr(self, 'dropout'):
            x = self.dropout(x)

        x = self.fc(x)
        # x = self.softmax(x)

        return x