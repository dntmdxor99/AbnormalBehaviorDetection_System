from socket import *
from select import select
import sys
from collections import deque
import time
import json
import requests
import cv2
import base64

inputs = [[1, 0.8], [2, 0.7], [2, 0.3], [1, 0.9], [2, 0.5],
         [5, 0.3], [4, 0.2], [3, 0.1], [2, 0.4], [1, 0.6],
         [1, 0.7], [1, 0.8], [2, 0.9], [3, 0.3], [4, 0.2],
         [5, 0.3], [3, 0.4], [1, 0.5], [2, 0.6], [3, 0.7],
         [2, 0.8], [1, 0.9], [0, 0.3], [5, 0.5], [4, 0.6],
         [3, 0.4], [2, 0.5], [1, 0.6], [1, 0.7], [5, 0.8],
         [4, 0.9], [3, 0.1], [2, 0.6], [1, 0.3], [2, 0.4],
         [5, 0.5], [4, 0.6], [3, 0.7], [2, 0.8], [1, 0.9],
         [3, 0.3], [5, 0.5], [4, 0.9], [3, 0.4], [2, 0.5],
         [1, 0.6], [0, 0.7], [5, 0.8], [4, 0.9], [3, 0.8]]
         
metaData = {
    "metaDataId" : None,
    "foundTime" : "",
    "entityFoundTime" : "",
    "cctvId" : 1,
    "type" : "PERSON",
    "abnormalType" : "1",
    "quality" : "",
    "videoId" : 2,
    "photoId" : 1,
    "base64Image"  : None
}

dq = deque()
prob = [0, 0, 0, 0, 0, 0]
cnt = [0, 0, 0, 0, 0, 0]

for i, input in enumerate(inputs):
    type, quality = input
    if len(dq) == 10:
        popType, popQuality = dq.popleft()
        prob[popType] -= popQuality
        cnt[popType] -= 1

    dq.append(input)
    prob[type] += quality
    cnt[type] += 1

    if prob[type] >= 2.0:
        metaData["abnormalType"] = type
        temp = prob[type] / cnt[type]
        if temp > 0.7:
            metaData["quality"] = "HIGH"
        elif temp > 0.5:
            metaData["quality"] = "MIDDLE"
        else:
            metaData["quality"] = "LOW"
        metaData["foundTime"] = time.strftime('%Y-%m-%dT%H:%M:%S')
        metaData["entityFoundTime"] = time.strftime('%Y-%m-%dT%H:%M:%S')

        image = cv2.imread('00000001.jpg')
        # image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        image_encode = cv2.imencode('.jpg', image)
        b64_string = base64.b64encode(image_encode[1]).decode('utf-8')
        metaData["base64Image"] = b64_string

        s = requests.Session()
        s.post(url = "http://118.45.212.161:8080/metadata/create", json = metaData)

        dq.clear()
        prob = [0, 0, 0, 0, 0, 0]
        cnt = [0, 0, 0, 0, 0, 0]