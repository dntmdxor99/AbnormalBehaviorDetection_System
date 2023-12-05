import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import { useRecoilValue } from "recoil";
import abnormalBehaviorState from "../recoil/abnormalBehaviorState";

const Rectangle = styled.div`
  width: 400px;
  height: 100%;
  background: #f5f7fa;
  position: absolute;
  left: 0;
`;

const Box = styled.div`
  margin-top: 80px;
  margin-left: 50px;
  margin-right: 40px;
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

const SelectionButton = styled.div`
  display: inline-block;
  padding: 15px 15px;
  font-size: 14px;
  margin: 4px;
  background-color: #3a3d92;
  color: #ffffff;
  transition: background-color 0.3s;
  text-decoration: none;
  border-radius: 5px;
  // border: 2px solid #3a3d92;
`;

const ResultPage = () => {
  const selectedAbnormalBehaviors = useRecoilValue(abnormalBehaviorState);

  return (
    <div>
      <Rectangle>
        <Box>
          <Types>
            검색 세부 정보
            <Contents>위치</Contents>
            <Contents>구간</Contents>
          </Types>
          <Types>이상 행동 유형
          <Contents>
            <div>
              {selectedAbnormalBehaviors.map((behavior, index) => (
                <SelectionButton key={index}>{behavior}</SelectionButton>
              ))}
            </div>
          </Contents>
          </Types>
          <Types>CCTV</Types>
        </Box>
      </Rectangle>
      <h1>hi</h1>
    </div>
  );
};

export default ResultPage;
