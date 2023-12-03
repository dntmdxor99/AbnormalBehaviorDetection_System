import { useEffect } from "react";
import { MapMarker } from "react-kakao-maps-sdk";

const InsideMap = ({ positions }) => {

  return (
    <>
        {positions.map((position) => (
            <MapMarker
                key={position.cctvId}
                position={{lat: position.latitude, lng: position.longitude}}
                onClick={() => {
                    console.log("click");
                }}
            />
        ))}
    </>
)
};

export default InsideMap;