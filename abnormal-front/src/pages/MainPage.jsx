import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import homeImage from "../assets/img/home-image.png";
import { Link } from "react-router-dom";
import Button from "@mui/material/Button";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const MainPage = () => {
  return (
    <PageLayout>
      <Frame>
        <div className="home">
          <div className="home-main">
            <h1 className="home-main-title">
              다채널 CCTV
              <br />
              이상행동 감지 및 추적
            </h1>
            <div className="home-to-search">
              <Link to="/search">
                <Button variant="contained">검색하기</Button>
              </Link>
            </div>
          </div>
          <div className="home-image">
            <img
              className="home-image-proto"
              alt="decoration"
              src={homeImage}
            />
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default MainPage;
