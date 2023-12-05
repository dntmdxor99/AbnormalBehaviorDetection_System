import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { Map, MapMarker } from "react-kakao-maps-sdk";

import PageLayout from "../components/PageLayout";
import InsideMap from "../components/InsideMap";
import API from "../utils/API.js";
import useUserPosition from "../hooks/useUserPosition";
import useWindowSize from "../hooks/useWindowSize";
import "../App.css";
import cctvIdState from "../recoil/cctvIdState.js";
import { useRecoilState } from "recoil";

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

const Box = styled.div`
  margin-top: 80px;
  margin-left: 50px;
  margin-right: 30px;
`;

const Types = styled.div`
  font-size: 25px;
  font-style: normal;
  font-weight: 700;
  margin-bottom: 40px;
`;

const Contents = styled.div`
  margin-top: 15px;
  font-size: 20px;
  font-style: normal;
  font-weight: 500;
  line-height: 0.5;
`;

const RadioButtonGroup = styled.div`
  margin-top: 15px;
  margin-bottom: 20px;
  margin-left: 10px;
  font-size: 15px;
  font-style: normal;
  font-weight: 400;
  line-height: 1.5;
`;

const RadioButton = styled.div`
  display: block;
`;

const abnormalBehaviors = ["싸움", "폭행", "주취행동", "기절", "납치"];

const SelectionButton = styled.div`
  display: inline-block;
  padding: 10px 10px;
  font-size: 15px;
  margin: 5px;
  background-color: ${(props) => (props.active ? "#3a3d92" : "#ffffff")};
  color: ${(props) => (props.active ? "#ffffff" : "#3a3d92")};
  transition: background-color 0.3s;
  text-decoration: none;
  border-radius: 5px;
  border: 2px solid #3a3d92;

  &:hover {
    background-color: #3a3d92;
    color: #ffffff;
  }
`;

function SearchPage() {
  const windowSize = useWindowSize();
  const userPosition = useUserPosition();
  const [activeStates, setActiveStates] = useState(
    Array(abnormalBehaviors.length).fill(false)
  );

  const handleClick = (index) => {
    const newActiveStates = [...activeStates];
    newActiveStates[index] = !newActiveStates[index];
    setActiveStates(newActiveStates);
  };

  // const [cctvData, setCctvData] = useState([]);
  // [{
  //   cctvId: "",
  //   cctvName: "",
  //   location: "",
  //   latitude: "",
  //   longitude: "",
  //   is360Degree: "",
  //   channel: "",
  //   videoSize: "",
  // }]

  /*
  {
    "cctvId": "",
    "cctvName": "",
    "location": "대구광역시 북구 대현로15길 17",
    "is360Degree": true,
    "channel" : ""
}
  */

  const [positions, setPositions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await API.get("/cctvs/allCctv");
        if (response.status === 200) {
          const data = response.data;
          console.log(data);
          setPositions(data);
        } else {
          console.error("API 호출 실패");
        }
      } catch (error) {
        console.error("CCTV 데이터를 받아오는데 실패했습니다.", error);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    console.log(positions);
  }, [positions]);

  const [positionData, setPositionData] = useRecoilState(cctvIdState);

  return (
    <PageLayout>
      <Frame>
        <div className="search">
          <div className="search-main">
            <MapContainer>
              <Rectangle>
                <Box>
                  <Types>
                    선택된 CCTV
                    <Contents>ID</Contents>
                    <Contents>위치</Contents>
                  </Types>

                  <Types>
                    검색설정
                    <Contents>검색 기간</Contents>
                    <RadioButtonGroup>
                      <RadioButton>
                        <input
                          type="radio"
                          name="searchPeriod"
                          value="real-time"
                        />
                        실시간
                      </RadioButton>
                      <RadioButton>
                        <input
                          type="radio"
                          name="searchPeriod"
                          value="set-time"
                        />
                        구간 설정
                      </RadioButton>
                    </RadioButtonGroup>
                    <Contents>
                      이상행동 선택
                      <div>
                        {abnormalBehaviors.map((behavior, index) => (
                          <SelectionButton
                            key={index}
                            active={activeStates[index]}
                            onClick={() => handleClick(index)}
                          >
                            {behavior}
                          </SelectionButton>
                        ))}
                      </div>
                    </Contents>
                  </Types>
                </Box>
                <Link to="/result">Result Page로 이동</Link>
              </Rectangle>
              <Map
                center={userPosition}
                style={{
                  width: `${windowSize.width}px`,
                  height: `${windowSize.height}px`,
                }}
                level={3}
              >
                <InsideMap positions={positions} />
              </Map>
            </MapContainer>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
}

export default SearchPage;
