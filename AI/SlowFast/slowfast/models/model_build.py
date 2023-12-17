from .slowfast import SlowFast
import torch


def modelBuild(opt):
    model = SlowFast(opt)
    # Move model to GPU if available.
    if opt['numGpus'] > 0:
        assert torch.cuda.is_available() is True
        model = model.cuda(device=torch.cuda.current_device())
        opt['device'] = 'cuda'

    return model
