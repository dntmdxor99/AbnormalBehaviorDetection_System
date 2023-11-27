import { useEffect } from "react";
import { useMap } from "react-kakao-maps-sdk";

const InsideMap = ({ cctvData }) => {
  const map = useMap();

  useEffect(() => {
    if (map) {
      cctvData.forEach(cctv => {
        new window.kakao.maps.Marker({
          map: map,
          position: new window.kakao.maps.LatLng(cctv.location.lat, cctv.location.lng) // 마커의 위치
        });
      });
    }
  }, [map, cctvData]);

  return null;
};

export default InsideMap;
