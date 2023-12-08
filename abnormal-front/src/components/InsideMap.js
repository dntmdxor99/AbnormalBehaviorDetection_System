import { useEffect } from "react";
import { MapMarker } from "react-kakao-maps-sdk";
import KakaoMarker from "./KakaoMarker";

const InsideMap = ({ positions }) => {
  return (
    <>
      {positions.map((position) => (
        <KakaoMarker
          key={position.cctvId}
          cctvId={position.cctvId}
          lat={position.latitude}
          lng={position.longitude}
        />
      ))}
    </>
  );
};

export default InsideMap;
