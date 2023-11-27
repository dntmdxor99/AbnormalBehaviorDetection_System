import {useState} from "react";
import defaultMarkers from "../constants/defaultMarkers";
import createMarker from "../utils/marker";

const useMarkers = map => {
    const [markers, setMarkers] = useState(defaultMarkers);

    // markers.forEach(marker => createMarker(map, marker.position, marker.clickable, marker.image));

    return markers;
}

export default useMarkers;