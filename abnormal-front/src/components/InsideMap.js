import {useMap} from "react-kakao-maps-sdk";

export default function InsideMap() {
    const map = useMap();
    console.log(map);

    return (
        <div>InsideMap</div>
    );
}