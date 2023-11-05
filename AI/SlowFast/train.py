import torch
from torchsummary import summary 
from slowfast.models.model_build import modelBuild
from slowfast.utils.options import parseOptions


if __name__ == "__main__":
    opt = parseOptions()
    model = modelBuild(opt)
    print(summary(model, [(3, 4, 224, 224), (3, 32, 224, 224)]))