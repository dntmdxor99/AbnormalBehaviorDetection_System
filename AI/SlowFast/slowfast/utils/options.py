import argparse
import yaml
import os


def yamlLoad(f):
    if os.path.isfile(f):
        with open(f, 'r', encoding='utf-8') as f:
            return yaml.load(f, Loader=yaml.FullLoader)
    else:
        return yaml.load(f, Loader=yaml.FullLoader)


def parseOptions():
    parser = argparse.ArgumentParser()
    parser.add_argument('-opt', type = str, required=True, help='Path to option YAML file.')      # yaml file 경로를 입력,
    parser.add_argument('-gpu', type = int, help = 'Insert GPU Number')
    parser.add_argument('-env', type = str, help = 'Insert your environment (Mac, Ubuntu)')
    args = parser.parse_args()

    opt = yamlLoad(args.opt)
    backbone = opt['backbone']
    opt['middleDim'] = opt[backbone]['middleDim']
    opt['outputDim'] = opt[backbone]['outputDim']
    opt['stageBlocks'] = opt[backbone]['stageBlocks']

    if args.gpu is None:
        opt['numGpus'] = 0
    else:
        opt['numGpus'] = args.gpu
    
    if opt['numGpus'] >= 1:
        opt['device'] = 'cuda'
    else:
        opt['device'] = 'cpu'

    if args.env is None:
        opt['env'] = 'Ubuntu'
    else:
        opt['env'] = args.env

    if opt['name'] == 'train':
        opt['trainDataPath'] = opt[f'{opt["env"]}_trainDataPath']
        opt['valDataPath'] = opt[f'{opt["env"]}_valDataPath']
        opt['trainMetaInfoFile'] = opt[f'{opt["env"]}_trainMetaInfoFile']
        opt['valMetaInfoFile'] = opt[f'{opt["env"]}_valMetaInfoFile']
    else:
        opt['testDataPath'] = opt[f'{opt["env"]}_testDataPath']
        opt['pretrainedModelPath'] = opt[f'{opt["env"]}_pretrainedModelPath']


    return opt