import InsideMap from "./InsideMap";
import useWindowSize from "../hooks/useWindowSize";
import useUserPosition from "../hooks/useUserPosition";
import {useEffect} from "react";
import {Map} from "react-kakao-maps-sdk";

export default function KakaoMap() {
    const windowSize = useWindowSize();

    const userPosition = useUserPosition();

    useEffect(() => {
        console.log(userPosition);
    }, []);


    return (
        <Map
            center={userPosition}
            style={{
                width: `${windowSize.width * 0.6}px`,
                height: `${windowSize.height}px`
            }}
            level={3}
        >
            <InsideMap cctvData={[]}/>
        </Map>);
}