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
    parser.add_argument('-opt', type=str, required=True, help='Path to option YAML file.')      # yaml file 경로를 입력,
    args = parser.parse_args()

    opt = yamlLoad(args.opt)
    backbone = opt['backbone']
    opt['middleDim'] = opt[backbone]['middleDim']
    opt['outputDim'] = opt[backbone]['outputDim']
    opt['stageBlocks'] = opt[backbone]['stageBlocks']

    return opt