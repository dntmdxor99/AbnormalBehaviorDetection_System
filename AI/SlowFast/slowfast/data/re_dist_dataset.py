import os
import random

label = {'fight' : 1, 'datefight' :1, 'assault' : 2, 'drunken' : 3, 'swoon' : 4, 'kidnap' : 5}
cnt = [0] * len(label)
data = [[] for _ in range(6)]
distData = [[] for _ in range(6)]

with open('./meta_info/meta_info_train.txt', 'r') as f:
    lines = f.readlines()
    # print(lines)
    for line in lines:
        clip, type = line.strip().split()
        cnt[int(type)] += 1
        data[int(type)].append((clip, type))
    
min = min(cnt)
for i in range(6):
    random.shuffle(data[i])
    distData[i] = data[i][:min]

distData = sum(distData, [])
random.shuffle(distData)
random.shuffle(distData)

with open('./meta_info/meta_info_train_dist.txt', 'w') as f:
    for clip, type in distData:
        f.write(f'{clip} {type}\n')


