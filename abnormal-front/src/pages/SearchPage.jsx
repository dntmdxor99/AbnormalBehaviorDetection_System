
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Map } from 'react-kakao-maps-sdk';



import PageLayout from "../components/PageLayout";
import InsideMap from "../components/InsideMap";
import API from "../utils/API.js";
import useUserPosition from "../hooks/useUserPosition";
import useWindowSize from "../hooks/useWindowSize";
import '../App.css';

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
  line-height: 0.5
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

const SearchPage = () => {
  const windowSize = useWindowSize();
  const userPosition = useUserPosition();
  const [cctvData, setCctvData] = useState([]);

  let positions = [
    {
      cctvId: "",
      cctvName: "",
      location: "",
      is360Degree: "",
      protocol: "",
      videoSize: ""
    }
  ]
  
  useEffect(() => {
    console.log("ooooooooooooooooooooooo");
    API.get('/cctvs/allCctv')
      .then(response => {
        positions = response.data.map(item => ({
          cctvId: item.cctvId,
          cctvName: item.cctvName,
          location: item.location,
          is360Degree: item.is360Degree,
          protocol: item.protocol,
          videoSize: item.videoSize,
        }))
        console.log('서버 응답:', response.data);
      })
      .catch(error => {
        console.log("에러부분임-----------------");
        console.error('CCTV 데이터를 받아오는데 실패했습니다.', error);
      });
  }, []);

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
                  height: `${windowSize.height}px`
                }}
                level={3}
              >
                <InsideMap cctvData={cctvData} />
              </Map>
            </MapContainer>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default SearchPage;