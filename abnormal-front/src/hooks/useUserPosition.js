import { useEffect, useState } from "react";

const useUserPosition = () => {
  const [userPosition, setUserPosition] = useState({
    lat: 0,
    lng: 0,
  });

  useEffect(() => {
    const getUserPosition = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            setUserPosition({
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            });
          },
          (error) => {
            console.error(error);
            // 에러 처리 로직 추가
          },
          {
            enableHighAccuracy: true, // 위치 정확도 설정
          }
        );
      } else {
        console.error("Geolocation is not supported.");
        // 지원되지 않는 경우에 대한 오류 처리
      }
    };

    getUserPosition();
  }, []);

  return userPosition;
};

export default useUserPosition;