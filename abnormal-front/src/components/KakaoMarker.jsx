import { MapMarker } from "react-kakao-maps-sdk";
import { useState, useEffect } from "react";
import { useSetRecoilState, useRecoilValue, useRecoilState } from "recoil";
import cctvIdState from "../recoil/cctvIdState";
import markerGreen from "../assets/img/markerGreen.png"
import markerRed from "../assets/img/markerRed.png"


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
  const [cctvIdValue, setCctvIdValue] = useRecoilState(cctvIdState);

  useEffect(() => {
    console.log(cctvIdValue);
  }, [cctvIdValue]);

  const handleMarkerClick = () => {
    setIsClicked(!isClicked);
    checkCctvIdState(cctvId);
    //   console.log(cctvIdValue);
  };
  //   console.log("0000000000");

  const checkCctvIdState = (cctvId) => {
    let cctvIdValues = [...cctvIdValue];

    if (cctvIdValues.includes(cctvId)) {
      setCctvIdValue(cctvIdValues.filter((value) => value !== cctvId));
    } else {
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
              src: markerGreen,
              size: new window.kakao.maps.Size(35, 35),
              alt: "마커 이미지",
            }
          : {
              src: markerRed,
              size: new window.kakao.maps.Size(35, 35),
              alt: "마커 이미지",
            }
      }
    />
  );
};

export default KakaoMarker;
