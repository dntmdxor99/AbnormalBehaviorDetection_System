import os

label = {'fight' : 1, 'datefight' :1, 'assault' : 2, 'drunken' : 3, 'swoon' : 4, 'kidnap' : 5}
cnt = [0] * len(label)
cntFrame = [0] * len(label)

with open('./meta_info/meta_info_train.txt', 'r') as f:
    lines = f.readlines()
    for line in lines:
        clip, type = line.split()
        cnt[int(type)] += 1

        cntFrame[int(type)] += len(os.listdir(f'/home/woo/Desktop/job/project/AbnormalBehaviorDetection_System/AI/SlowFast/datasets/train/{clip}'))

print(cnt)
print(cntFrame)
