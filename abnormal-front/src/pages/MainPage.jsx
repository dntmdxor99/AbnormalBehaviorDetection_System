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
import Swal from "sweetalert2";


const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const SearchButtonStyle = {
  color: '#fff',
  backgroundColor: '#000080',
  padding: '17px 50px',
  borderRadius: '30px',
  fontWeight: 'bold',
  fontSize: '20px',
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
  font-size: 52px;
  margin-left: 220px;
  maring-bottom: 100px;
`;

const HomeToSearch = styled.div`
  margin-bottom: 20px;
  margin-top: 20px;
  margin-left: 220px;
`;

const HomeImage = styled.div`
  flex: 1;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
`;

const ImageProto = styled.img`
  max-width: 70%;
  max-height: 50%;
  height: auto;
  margin-right: 120px;
`;

const MainPage = () => {
  const navigate = useNavigate();
  const {user} = useAuth();

  const handleSearchClick = () => {
    if (!user) {
      Swal.fire({
        title: '로그인 후 이용할 수 있습니다.',
        icon: 'info',
        confirmButtonColor: '#000080',
        confirmButtonText: '확인',
      }).then((result) => {
        if (result.isConfirmed) {
          navigate('/login');
        }
      });
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
                <div style={{display: 'flex', alignItems: 'center'}}>
                  <img
                    src={require('../assets/img/Vector.png')}
                    alt="Search Icon"
                    style={{ marginRight: '10px' }}
                  />
                </div>
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
