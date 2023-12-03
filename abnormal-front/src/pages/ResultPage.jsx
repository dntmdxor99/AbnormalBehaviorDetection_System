import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import { hi } from "date-fns/locale";

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
`;
const Types = styled.div`
  font-size: 25px;
  font-style: normal;
  font-weight: 700;
  margin-bottom: 40px;
`;

const Contents = styled.div`
  font-size: 20px;
  font-style: normal;
  font-weight: 400;
`;

const ResultPage = () => {
  return (
    <div>
      <Rectangle>
        <Box>
          <Types>
            검색 세부 정보
            <Contents>위치</Contents>
            <Contents>구간</Contents>
          </Types>
          <Types>이상 행동 유형</Types>
          <Types>CCTV</Types>
        </Box>
      </Rectangle>
      <h1>hi</h1>
    </div>
  );
};

export default ResultPage;
