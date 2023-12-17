import os

resultsDir = '/home/woo/Desktop/job/project/AbnormalBehaviorDetection_System/AI/SlowFast/results/newnew'
resultsPath = os.listdir(resultsDir)

for result in resultsPath:
    if result != '060':
       continue
    inputPath = os.path.join(resultsDir, result)
    cmd = ['ffmpeg', 
           '-f', 'image2',
           '-r', '30',
           '-start_number', '280'
           '-i', f'{inputPath}/%8d.png',
           f'{resultsDir}/{result}.mp4']    
    
    os.system(' '.join(cmd))