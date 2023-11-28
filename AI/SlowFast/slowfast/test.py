import torch
from torchsummary import summary 

import torch.nn.functional as F

from models.model_build import modelBuild
from utils.options import parseOptions
# from data.dataset import VideoDataset
# from data.build_loader import construct_loader
from data.build_test_loader import construct_loader

from tqdm import tqdm


def num_topK_correct(preds, labels, k_list=(1, 3)):
    _, top_k_indices = torch.topk(preds, max(k_list))
    expanded_labels = labels.view(-1, 1).expand_as(top_k_indices)
    match_matrix = ((expanded_labels - top_k_indices) == 0)
    num_correct = [match_matrix[:, :k].sum() for k in k_list]
    return num_correct


def testPipeline():
    opt = parseOptions()
    device = opt['device']

    model = modelBuild(opt).to(device)
    model.load_state_dict(torch.load(opt['pretrainedModelPath'], map_location=device))
    model.eval()

    valLoader = construct_loader(opt, 'test')

    data_size = 0
    sum_top1_correct = 0
    sum_top5_correct = 0

    with tqdm(total = len(valLoader), desc = 'testing') as pbar:
        with torch.no_grad():
            for i, (slowData, fastData) in enumerate(valLoader):
                # Transfer the data to the current GPU device.
                if opt['numGpus'] > 0:
                    # for i in range(len(inputs)):
                        # inputs[i] = inputs[i].cuda(non_blocking=True)
                    slowData = slowData.cuda(non_blocking=True)
                    fastData = fastData.cuda(non_blocking=True)

                preds = model(slowData, fastData)
                print(preds)
                print(int(torch.argmax(preds)))
                # top1_correct, top3_correct = num_topK_correct(preds, label, (1, 3))

                # # Evaluating stats
                # sum_top1_correct += top1_correct
                # sum_top5_correct += top3_correct
                # data_size += len(label)

        pbar.update()

        # print(f'top1 acc: {sum_top1_correct / data_size * 100: .4f}%, '
        #     f'top5 acc: {sum_top5_correct / data_size * 100: .4f}%', flush=True)

        
if __name__ == "__main__":
    testPipeline()