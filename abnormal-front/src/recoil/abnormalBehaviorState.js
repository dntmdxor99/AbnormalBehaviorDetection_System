import { atom } from 'recoil';
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist({
  key: "localStorage",
  storage: localStorage,
})

const abnormalBehaviorState = atom({
  key: 'abnormalBehaviorState',
  default: [],
  effects_UNSTABLE: [persistAtom]
});

export default abnormalBehaviorState;