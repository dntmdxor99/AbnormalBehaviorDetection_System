import { useEffect } from "react";

const useMouseClickEventListener = (map, callback) => {
  useEffect(() => {
    if (map) {
      window.kakao.maps.event.addListener(map, 'click', callback);
    }
  }, [map, callback]);
};

export default useMouseClickEventListener;