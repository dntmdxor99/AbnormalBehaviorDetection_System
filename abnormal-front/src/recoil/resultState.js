// [
//     {
//         "metaDataId": 1,
//         "foundTime": "2020-08-06T03:04:00.000+00:00",
//         "entityFoundTime": "2020-08-06T03:05:00.000+00:00",
//         "cctvId": 1,
//         "type": "PERSON",
//         "abnormalType": "fight",
//         "quality": "HIGH",
//         "videoId": 1,
//         "photoId": 1,
//         "base64Image": 1000000000,
//     }
// ]

import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist({
  key: "localStorage",
  storage: localStorage,
});

const resultState = atom({
  key: "resultState",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default resultState;
