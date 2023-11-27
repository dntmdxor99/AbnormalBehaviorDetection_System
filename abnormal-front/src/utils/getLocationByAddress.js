const getLocationByAddress = async address => {
    const geocoder = new window.kakao.maps.services.Geocoder();

    return await new Promise((resolve) => {
        geocoder.addressSearch(address, (res) =>
            resolve(new window.kakao.maps.LatLng(res[0].y, res[0].x))
        );
    });
};

export default getLocationByAddress;
