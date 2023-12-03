import os

path = '/home/woo/Desktop/job/project/AbnormalBehaviorDetection_System/AI/SlowFast/datasets'
name = ['train', 'val', 'test']
subDirCount = [os.listdir(os.path.join(path, name[x])) for x in range(3)]

for i in range(3):
    cnt = 0
    for j in range(len(subDirCount[i])):
        cnt += len(os.listdir(os.path.join(path, name[i], subDirCount[i][j])))
    print(f'{name[i]}: {cnt}')