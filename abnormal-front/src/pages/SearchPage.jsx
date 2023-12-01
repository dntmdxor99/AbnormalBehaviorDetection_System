import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Map } from "react-kakao-maps-sdk";
import { MapMarker } from "react-kakao-maps-sdk";

import PageLayout from "../components/PageLayout";
import InsideMap from "../components/InsideMap";
import API from "../utils/API.js";
import useUserPosition from "../hooks/useUserPosition";
import useWindowSize from "../hooks/useWindowSize";
import "../App.css";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
  position: relative;
`;

const MapContainer = styled.div`
  position: relative;
`;

const Rectangle = styled.div`
  position: absolute;
  top: 30px;
  right: 30px;
  bottom: 30px;
  width: 380px;
  background-color: #f5f7fa;
  border-radius: 20px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  z-index: 2;
`;

const SelectCCTV = styled.p`
  margin-top: 80px;
  margin-left: 35px;
  font-size: 25px;
  font-style: normal;
  font-weight: 700;
`;

const SelectCCtvContents = styled.p`
  margin-left: 35px;
  font-size: 20px;
  font-style: normal;
  font-weight: 500;
  line-height: 0.5;
`;

const SearchSettings = styled.p`
  margin-top: 65px;
  margin-left: 35px;
  font-size: 25px;
  font-style: normal;
  font-weight: 700;
`;

const SearchSettingsContents = styled.p`
  margin-left: 45px;
  font-size: 18px;
  font-style: normal;
  font-weight: 500;
  line-height: 0.3;
`;

const RadioButtonGroup = styled.div`
  margin-left: 40px;
  font-size: 15px;
  font-style: normal;
  font-weight: 400;
  line-height: 1.5;
`;

const RadioButton = styled.div`
  display: block;
`;

function SearchPage() {
  const windowSize = useWindowSize();
  const userPosition = useUserPosition();
  const [cctvData, setCctvData] = useState([]);
  const [positions, setPositions] = useState({
    cctvId: "",
    cctvName: "",
    location: "",
    latitude: "",
    longitude: "",
    is360Degree: "",
    protocol: "",
    videoSize: "",
  });


  useEffect(() => {
    //console.log("ooooooooooooooooooooooo");
    const fetchData = async () => {
      try {
        //세아두번호출됨 refactoring해야함
        const response = await API.get("/cctvs/allCctv");
        console.log("ooooooooooooooooooooooo1");

        //console.log(response);
        if (response.status === 200) {
          const data = response.data;
          console.log("ooooooooooooooooooooooo2");
          console.log(data);
          setPositions(data);
          // console.log("oooooooooo111111112");
          // console.log(positions);
        } else {
          console.error("API 호출 실패");
        }
      } catch (error) {
        console.log("에러부분임-----------------");
        console.error("CCTV 데이터를 받아오는데 실패했습니다.", error);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    console.log("oooooooooo111111112");
    console.log(positions);
  }, []);

  // const [markerPosition, setMarkerPosition] = useState(); // 클릭한 위치를 저장할 상태
  // const onMapClick = (event) => {
  //   console.log(event); // 이벤트 객체 구조 확인
  // };

  return (
    <PageLayout>
      <Frame>
        <div className="search">
          <div className="search-main">
            <MapContainer>
              <Rectangle>
                <SelectCCTV>선택된 CCTV</SelectCCTV>
                <SelectCCtvContents>ID</SelectCCtvContents>
                <SelectCCtvContents>위치</SelectCCtvContents>
                <SearchSettings>검색설정</SearchSettings>
                <SearchSettingsContents>검색 기간</SearchSettingsContents>
                <RadioButtonGroup>
                  <RadioButton>
                    <input type="radio" name="searchPeriod" value="real-time" />
                    실시간
                  </RadioButton>
                  <RadioButton>
                    <input type="radio" name="searchPeriod" value="set-time" />
                    구간 설정
                  </RadioButton>
                </RadioButtonGroup>
                <SearchSettingsContents>이상행동 선택</SearchSettingsContents>
              </Rectangle>
              <Map
                center={userPosition}
                style={{
                  width: `${windowSize.width}px`,
                  height: `${windowSize.height}px`,
                }}
                level={3}
                onClick={(event) => {
                  console.log(event);
                }}
              >
                {/* {markerPosition && <MapMarker position={markerPosition} />}{" "}
                markerPosition이 있을 때만 Marker 컴포넌트를 렌더링 */}
                <InsideMap cctvData={cctvData} />
              </Map>
            </MapContainer>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
}

export default SearchPage;