import {useEffect, useState} from "react";

const useUserPosition = () => {
    const [userPosition, setUserPosition] = useState({
        lat: 0,
        lng: 0,
    });

    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                setUserPosition({
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                });
            });
        }
    }, []);

    return userPosition;
}

export default useUserPosition;