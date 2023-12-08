import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import homeImage from "../assets/img/home-image.png";
import { Link } from "react-router-dom";
import Button from "@mui/material/Button";
import { useAuth } from "../context/AuthContext";


const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const SearchButtonStyle = {
  color: '#fff',
  backgroundColor: '#000080',
  padding: '5px 10px',
  borderRadius: '5px',
  fontWeight: 'bold',
  textDecoration: 'none',
}

const MainPageContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
`;

const HomeMain = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;

const HomeTitle = styled.h1`
  font-weight: bold;
`;

const HomeToSearch = styled.div`
  margin-top: 20px;
`;

const HomeImage = styled.div`
  flex: 1;
`;

const ImageProto = styled.img`
  max-width: 50%;
  height: auto;
`;

const MainPage = () => {
  const navigate = useNavigate();
  const {user} = useAuth();
  const onClickHandler = () => alert("로그인 후 사용가능합니다.");

  const handleSearchClick = () => {
    if (!user) {
      onClickHandler();
      navigate('/login');
    }
    else {
      navigate('/search');
    }
  };

  return (
    <PageLayout>
      <Frame>
        <MainPageContainer>
          <HomeMain>
            <HomeTitle>
              다채널 CCTV
              <br />
              이상행동 감지 및 추적
            </HomeTitle>
            <HomeToSearch>
              <Button onClick={handleSearchClick} style={SearchButtonStyle}>
                검색하기
              </Button>
            </HomeToSearch>
          </HomeMain>
          <HomeImage>
            <ImageProto
              className="home-image-proto"
              alt="decoration"
              src={homeImage}
            />
          </HomeImage>
        </MainPageContainer>
      </Frame>
    </PageLayout>
  );
};


export default MainPage;
