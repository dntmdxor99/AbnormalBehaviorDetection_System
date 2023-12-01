import os
import sys
import subprocess
from concurrent.futures import ProcessPoolExecutor
import random
import xml.etree.ElementTree as ET

xmls = [xml for xml in os.listdir('.') if xml.endswith('.xml')]
startDirName = os.listdir('./frame')
startDirName = int(max(os.listdir('./frame'))) if startDirName else 1
cnt = 0
cmds = []   

label = {'fight' : 1, 'assault' : 2, 'drunken' : 3, 'datefight' : 1}

with open('meta_info_train.txt', 'a') as f:
    for i, xml in enumerate(xmls):
        tree = ET.parse(xml)
        root = tree.getroot()
        objects = root.findall('object')
        time = root.find('header').find('duration').text.split(':')
        frameNum = int(root.find('header').find('frames').text)
        second = int(time[-2]) * 60 + int(time[-1].split('.')[0])
        videoName = root.find('filename').text
        actionName = root.find('folder').text
        
        actions = []
        frames = []
        toParse = []
        for x in objects:
            actions.extend(x.findall('action'))
        for x in actions:
            frames.extend(x.findall('frame'))
        for x in frames:
            toParse.append((int(x.find('start').text), int(x.find('end').text)))

        for start, end in toParse:
            duration = end - start
            startFrameIdx = [600, start]
            startTImeIdx = [int(second * (x / frameNum)) for x in startFrameIdx]
            timeDuration = int(second * (duration / frameNum)) + 0.2
            
            for i, startTIme in enumerate(startTImeIdx):
                if i == 0 and random.random() > 0.3:
                    continue
                if i == 1 and random.random() > 0.4:
                    continue

                outputDir = f'{str(startDirName + cnt).zfill(3)}'
                os.makedirs(f'./frame/{outputDir}', exist_ok=True)
                if i == 1:
                    f.write(f'{outputDir} {label[actionName]}\n')
                else:
                    f.write(f'{outputDir} 0\n')

                cmd = ['ffmpeg',
                    '-t', str(timeDuration),
                    '-ss', str(startTIme),
                        '-i', videoName,
                        '-vf', 'fps=30',
                        '-vf', 'scale=1920:1080',
                        f'./frame/{outputDir}/%08d.jpg'
                    ]
                cmds.append(' '.join(cmd))

                cnt += 1
            
                # os.system(' '.join(cmd))


def run_process(cmd):
    p = subprocess.Popen(cmd, shell = True)
    p.wait() 

with ProcessPoolExecutor(max_workers=4) as executor:
    executor.map(run_process, cmds)
