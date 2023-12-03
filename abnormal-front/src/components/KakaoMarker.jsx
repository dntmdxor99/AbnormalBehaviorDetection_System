import { MapMarker } from "react-kakao-maps-sdk";
import { useState } from "react";
import { useSetRecoilState, useRecoilValu, useRecoilState } from "recoil";
import cctvIdState from "../recoil/cctvIdState";

const KakaoMarker = ({ cctvId, lat, lng }) => {
  /*
  {
    "cctvId": "",
    "cctvName": "",
    "location": "대구광역시 북구 대현로15길 17",
    "is360Degree": true,
    "channel" : ""
}
  */

  const [isClicked, setIsClicked] = useState(false);
  const handleMarkerClick = () => {
    setIsClicked(!isClicked);
    checkCctvIdState(cctvId);
    console.log(cctvIdValue);
  };
//   console.log("0000000000");
  const [cctvIdValue, setCctvIdValue] =
    useRecoilState(cctvIdState);

  const checkCctvIdState = (cctvId) => {
    let cctvIdValues = [...cctvIdValue]

    if(cctvIdValues.includes(cctvId)){
        setCctvIdValue(cctvIdValues.filter(value => value !== cctvId));
    }
    else{
        setCctvIdValue([...cctvIdValues, cctvId]);
    }
  };

  return (
    <MapMarker
      cctvId={cctvId}
      position={{ lat: lat, lng: lng }}
      onClick={handleMarkerClick}
      image={
        isClicked
          ? {
              src: "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
              size: new window.kakao.maps.Size(24, 35),
              alt: "마커 이미지",
            }
          : {
              src: "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
              size: new window.kakao.maps.Size(24, 35),
              alt: "마커 이미지",
            }
      }
    />
  );
};

export default KakaoMarker;
