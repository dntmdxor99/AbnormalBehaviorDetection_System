import {useMap} from "react-kakao-maps-sdk";

const createMarker = (map, position, clickable = true, image = null) => {
    const {kakao} = window;

    const newMarker = kakao.maps.Marker({
        position
    });

    newMarker.setMap(map);
}

export default createMarker;