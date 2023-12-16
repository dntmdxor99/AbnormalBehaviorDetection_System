import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Button from '@mui/material/Button';
import styled, {createGlobalStyle} from 'styled-components';
import { useNavigate } from 'react-router-dom/dist';
import Swal from "sweetalert2";
import groupImage from '../assets/img/Group 1.png'


const loginButtonStyle = {
  color: '#000080',
  backgroundColor: '#f5F7Fa',
  marginRight: '15px',
  padding: '5px 10px',
  borderRadius: '5px',
  fontWeight: 'bold'
};


const signButtonStyle = {
  color: '#fff',
  backgroundColor: '#000080',
  padding: '5px 10px',
  borderRadius: '5px',
  fontWeight: 'bold'
};


const logoutButtonStyle = {
  color: '#000080',
  backgroundColor: '#f5F7Fa',
  padding: '5px 10px',
  borderRadius: '5px',
  fontWeight: 'bold'
};


const HeaderContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: white;
`;


const MenuBar = styled.div`
  display: flex;
  align-items: center;
  flex-shrink: 0;
  margin-left: 15px;
`;

const MenuItem = styled.h4`
  margin-left: 50px;
  margin-right: 40px;
  cursor: pointer;
  flex-shrink: 0;
`;


const Logo = styled.img`
  width: 10px;
  height: 20px;
  margin-right: -15px;
  margin-bottom: 5px;
`

const MenuBarButtons = styled.div`
  display: flex;
  align-items: center;

  span {
    margin-right: 50px;
  }

  button {
    margin-left: 20px;
  }

  a {
    text-decoration: none;
  }
`;


const fontStlye = {
  color: 'black',
  textDecoration: 'none',
  fontWeight: 'bold'
};


const blueFontStyle = {
  color: '#000080',
  textDecoration: 'none',
  fontWeight: '900',
};


const Header = () => {
  const navigate = useNavigate();
  const { user, logout, loading } = useAuth();


  const handleHeaderClick = (path) => {
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
      navigate(path);
    }

  }


  return (
    <HeaderContainer>
      <MenuBar>
          <MenuItem>
            <Logo src={groupImage} alt='Group Logo' />
            <Link to="/"  style={blueFontStyle} className="menu-home-item">
              7팀
            </Link>
          </MenuItem>
          <MenuItem>
            <div style={fontStlye} className="menu-bar-item" onClick={() => handleHeaderClick('/abnormal')}>
              이상행동
            </div>
          </MenuItem>
          <MenuItem>
            <div style={fontStlye} className="menu-bar-item" onClick={() => handleHeaderClick('/cctv')}>
              CCTV
            </div>
          </MenuItem>
          <MenuItem>
            <div style={fontStlye} className="menu-bar-item" onClick={() => handleHeaderClick('/ask')}>
              문의하기
            </div>
          </MenuItem>
      </MenuBar>
      
      {user !== null && !loading && (
        <MenuBarButtons>
          <>
            <span></span>
            <Link to="/" style={logoutButtonStyle} onClick={logout}>
              로그아웃
            </Link>
          </>
        </MenuBarButtons>
      )}

      {user === null && !loading && (
        <MenuBarButtons>
          <>
            <Link to="/login" style={loginButtonStyle}>
              로그인
            </Link>
            <Link to="/signup" style={signButtonStyle}>
              가입하기
            </Link>
          </>
        </MenuBarButtons>
      )}
    </HeaderContainer>
  );
};

export default Header;


--AuthContext.js-- (context 폴더 안)

import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';
import API from '../utils/API';


const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const login = (userData) => {
    setUser({userData: userData.username, token: userData.token});
    localStorage.setItem('login-token', userData.token);
    localStorage.setItem('user', JSON.stringify({username: userData.username, token: userData.token}));
  };

  const logout =  async () => {
    const token = localStorage.getItem('login-token');
    console.log(token);
    try {
      await API.get('/users/logout', {
        headers: {
          Authorization:`Bearer ${token}`
        }
      });

      setUser(null);
      localStorage.removeItem('login-token');
      localStorage.removeItem('user');
    } catch (error) {
      console.error('로그아웃 중 에러 발생', error);
    }
  };


  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('login-token');
      const storedUser = localStorage.getItem('user');
      console.log('Stored User:', storedUser);
      if (token && storedUser) {
        try {
          // const response = await API.get('/users/login', {
          //   headers: {
          //     Authorization: `Bearer ${token}`
          //   }
          // });
          setUser(JSON.parse(storedUser));
        } catch (error) {
          console.log("오류1");
          console.error('오류 발생', error);
        } finally {
          // API 호출이 완료되면 로딩 상태를 false로 변경
          setLoading(false);
        }
      } else {
        // 토큰이 없을 경우에도 로딩 상태를 false로 변경
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);


  return (
    <AuthContext.Provider value={{ user, login, logout, loading, setUser}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

--Popup.js-- (component 폴더 안)

import React from "react";
import styled from "styled-components";
import cancelIcon from '../assets/img/cancel.png';


const PopupContainer = styled.div`
  position: fixed;
  top: 55%;
  left: 35%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  z-index: 1000;
  width: 900px;
  height: 500px;
  margin: 0;
`;

const Header = styled.div`
  background-color: #DDDDDD;
  color: #000000;
  margin-bottom: -25px;
  width: 100%;
  height: 50px;
  box-sizing: border-box;
`

const Body = styled.div`
  width: 100%;
  height: 100%
`

const CloseButton = styled.button`
  position: absolute;
  right: 10px;
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

const CloseIcon = styled.img`
  width: 30px;
  height: 30px;
`

const VideoContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

// const Video = styled.video`
//   width: 100%;
//   height: auto;
// `;

const Popup = ({ onClose }) => {
  return (
    <PopupContainer>  
      <Header>
        <CloseButton onClick={onClose}>
          <CloseIcon src={cancelIcon} alt="Close" />
        </CloseButton>
      </Header>
      <Body>
        <VideoContainer>
        </VideoContainer>
      </Body>
    </PopupContainer>
  );
};


export default Popup;


--LoginPage--

import React, { useEffect, useState } from "react";
import Header from "../components/Header.jsx";
import PageLayout from "../components/PageLayout.js";
import styled, { useTheme } from "styled-components";
import API from "../utils/API.js";
import { useNavigate } from 'react-router-dom';
import Modal from '../components/Modal.jsx';
import axios from "axios";
import { useAuth } from "../context/AuthContext.js";
import Swal from "sweetalert2";


const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;

  input {
    margin-bottom: 40px;
    padding: 8px;
    width: 420px;
    height: 50px;
  }

  button {
    background-color: #000080;
    color: #fff;
    width: 420px;
    height: 50px;
    padding: 10px;
    cursor: pointer;
    border: none;
  }
`;

const CenteredContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
`;

const LoginFormContainer = styled.div`
  background-color: #ffffff;
  padding: 65px;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;


const LoginPage = () => {
  const {login} = useAuth();
  const navigate = useNavigate();

  const [inputValue, setInputValue] = useState({
    id: '',
    password: '',
  });


  useEffect( () => {
    checkLoginStatus();
  }, []);

  
  const checkLoginStatus = async () => {
    const token = localStorage.getItem('login-token');

    if (token) {
      try {
        const response = await API.get('/users/login', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        console.log('사용자 정보:', response.data);
        login({ username: response.data.userData });
      } catch (error) {
        console.error('오류발생');
      }
    }
  };


  const inputChangeHandler = (e, name) => {
    const {value} = e.target;

    setInputValue((prevInputValue) => ({
      ...prevInputValue,
      [name]: value,
    }));
  };


  const handleLogin = async (e) => {
    e.preventDefault();

    const data = {
      userId: inputValue.id,
      userPassword: inputValue.password,
    };

    try {
      const response = await API.post('/users/login', data);

      if (response.status === 200) {
        login({username: data.userId});
        localStorage.clear()
        localStorage.setItem('login-token', response.data)
        
        checkLoginStatus();
        
        Swal.fire({
          title: '로그인 성공!',
          icon: 'success',
          confirmButtonColor: '#000080',
          confirmButtonText: '확인',
        }).then((result) => {
          if (result.isConfirmed) {
            navigate('/');
          }
        });
      }
    }
    catch (error) {
      Swal.fire({
        title: '아이디 또는 비밀번호가 일치하지 않습니다.',
        icon: 'warning',
        confirmButtonColor: '#000080',
        confirmButtonText: '확인',
      }).then((result) => {
        if (result.isConfirmed) {
          navigate('/login');
        }
      });
    }
  }; 


  return (
    <PageLayout>
      <CenteredContainer>
        <h1
          style={{ fontSize: "55px", marginBottom: "60px", marginTop: "-60px", fontWeight: 'bold'}}
        >
          로그인
        </h1>
        <LoginFormContainer>
          <LoginForm onSubmit={handleLogin}>
            <input 
              type="text" 
              placeholder="아이디" 
              required 
              name="userId"
              value={inputValue.id}
              onChange={(e) => inputChangeHandler(e, 'id')}
            />
            <input 
              type="password" 
              placeholder="비밀번호" 
              required 
              name="userPassword"
              value={inputValue.password}
              onChange={(e) => inputChangeHandler(e, 'password')}
            />
            <button type="submit">로그인</button>
          </LoginForm>
        </LoginFormContainer>
      </CenteredContainer>
    </PageLayout>
  );
};

export default LoginPage;


--SearchPage.jsx--

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Map } from "react-kakao-maps-sdk";
import "react-datepicker/dist/react-datepicker.css";
import { useRecoilValue, useSetRecoilState } from "recoil";

import PageLayout from "../components/PageLayout";
import InsideMap from "../components/InsideMap";
import API from "../utils/API.js";
import useUserPosition from "../hooks/useUserPosition";
import useWindowSize from "../hooks/useWindowSize";
import "../App.css";
import cctvIdState from "../recoil/cctvIdState.js";
import SelectDate from "../components/SelectDate.jsx";
import resultState from "../recoil/resultState";
import Popup from "../components/Popup.js";


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

const SelectionButton = styled.div`
  display: inline-block;
  padding: 15px 15px;
  font-size: 16px;
  margin: 5px;
  background-color: ${(props) =>
    props.active === "true" ? "#3a3d92" : "#ffffff"};
  color: ${(props) => (props.active === "true" ? "#ffffff" : "#3a3d92")};
  transition: background-color 0.3s;
  text-decoration: none;
  border-radius: 5px;
  // border: 2px solid #3a3d92;
  cursor: pointer;

  &:hover {
    background-color: #3a3d92;
    color: #ffffff;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const NextButton = styled.div`
  display: inline-block;
  padding: 10px 20px;
  margin: 5px;
  font-size: 20px;
  font-weight: 700;
  background-color: #029d65;
  color: #ffffff;
  text-decoration: none;
  border-radius: 5px;
  cursor: pointer;
`;

function SearchPage() {
  const abnormalType = ["fight", "assault", "drunken", "swoon", "kidnap"];
  const abnormalTypeKorean = {
    fight: "싸움",
    assault: "폭행",
    drunken: "주취행동",
    swoon: "기절",
    kidnap: "납치",
  };
  const windowSize = useWindowSize();
  const userPosition = useUserPosition();
  const selectedCctvIds = useRecoilValue(cctvIdState);
  const selectedCount = selectedCctvIds.length;
  const [activeStates, setActiveStates] = useState(
    Array(abnormalType.length).fill(false)
  );

  const [positions, setPositions] = useState([]);
  const [selectedDateRange, setSelectedDateRange] = useState([]);
  const cctvId = useRecoilValue(cctvIdState);
  const [locationData, setLocationData] = useState([]);
  const [selectedLocation, setSelectocation] = useState();
  const [isRealTimeSelected, setIsRealTimeSelected] = useState(true);
  const nav = useNavigate();

  const setRecoilResultState = useSetRecoilState(resultState);
  const [sendResult, setSendResult] = useState([]);
  
  const [showPopup, setShowPopup] = useState(false);
  const [popupContent, setPopupContent] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await API.get("/cctvs/allCctv");
        if (response.status === 200) {
          const data = response.data;
          console.log(data);
          let locations = [];
          for (let i = 0; i < data.length; i += 1) {
            locations.push(data[i].location);
          }
          setLocationData(locations);
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
    if (cctvId.length > 0) {
      const location = locationData[cctvId[cctvId.length - 1] - 1];
      setSelectocation(location);
    } else {
      setSelectocation([]);
    }
  }, [locationData, cctvId]);

  const handleClick = (index) => {
    const newActiveStates = [...activeStates];
    newActiveStates[index] = !newActiveStates[index];
    setActiveStates(newActiveStates);
  };

  const handleSearchPeriodChange = (event) => {
    const { value } = event.target;
    if (value === "real-time") {
      setIsRealTimeSelected(true);
  
      setShowPopup(true);
      setPopupContent(
        <Popup
          onClose={handleClosePopup}
        />
      );
    } else {
      setIsRealTimeSelected(false);
      setShowPopup(false);
      setPopupContent(null);
    }
  };

  const handleClosePopup = () => {
    setShowPopup(false);
    setPopupContent(null);
  };

  useEffect(() => {
    console.log("sendResult updated:", sendResult);
  }, [sendResult]);

  const createResult = () => {
    const result = [];
    for (let i = 0; i < cctvId.length; i += 1) {
      for (let j = 0; j < activeStates.length; j += 1) {
        if (activeStates[j] === true) {
          result.push({
            startDate: selectedDateRange[0],
            endDate: selectedDateRange[1],
            cctvId: cctvId[i],
            abnormalType: abnormalType[j],
          });
        }
      }
    }
    return result;
  };

  const blank = [{
    startDate: "",
    endDate: "",
    cctvId: "",
    abnormalType: "",
  }]

  useEffect(() => {
    console.log(cctvId, activeStates.length, selectedDateRange);
    const res = createResult();
    console.log("21345678654323456");
    console.log(res);
    if (res.length === 0) {
      console.log(blank);
      setSendResult(blank);
    } else {
      console.log(res);
      setSendResult(res);
    }
  }, [cctvId, activeStates, selectedDateRange]);

  const handleNextButtonClick = async () => {
    try {
      const response = await API.post("/metadata/Legend", sendResult);
      if (response.status === 200) {
        const data = response.data;
        console.log("데이터 수신 성공", data);
        console.log(data);
        setRecoilResultState(data);
        nav("/result");
      } else {
        console.error("API 호출 실패");
      }
    } catch (error) {
      console.error("데이터 전송 실패", error);
    }
  };

  const handleDate = (dateResult) => {
    setSelectedDateRange(dateResult);
  };

  return (
    <PageLayout>
      <Frame>
        <div className="search">
          <div className="search-main">
            <MapContainer>
              <Rectangle>
                <Box>
                  <Types>
                    선택된 CCTV | {selectedCount}개
                    <Contents>
                      ID : {cctvId.length > 0 ? cctvId[cctvId.length - 1] : ""}
                    </Contents>
                    <Contents style={{ fontSize: "16px" }}>
                      위치 : {selectedLocation}
                    </Contents>
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
                          onChange={handleSearchPeriodChange}
                        />
                        실시간
                      </RadioButton>
                      <RadioButton>
                        <input
                          type="radio"
                          name="searchPeriod"
                          value="set-time"
                          onChange={handleSearchPeriodChange}
                        />
                        구간 설정
                        {!isRealTimeSelected && (
                          <SelectDate handleDate={handleDate} />
                        )}
                      </RadioButton>
                    </RadioButtonGroup>
                    {showPopup && (
                      <Popup content={popupContent} onClose={handleClosePopup} />
                    )}
                    {!isRealTimeSelected && (
                      <Contents>
                        이상행동 선택
                        <div style={{ marginTop: "10px" }}>
                          {abnormalType.map((behavior, index) => (
                            <SelectionButton
                              key={index}
                              active={activeStates[index].toString()}
                              onClick={() => handleClick(index)}
                            >
                              {abnormalTypeKorean[behavior]}
                            </SelectionButton>
                          ))}
                        </div>
                      </Contents>
                    )}
                  </Types>
                </Box>
                <ButtonContainer>
                  {/*<Link to="/result">*/}
                  <NextButton onClick={handleNextButtonClick}>
                    검색 결과 보기
                  </NextButton>
                  {/*</Link>*/}
                </ButtonContainer>
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



--SignupPage.jsx--

import React, {useState} from 'react';
import { registerLocale, setDefaultLocale } from 'react-datepicker';
import ko from 'date-fns/locale/ko';
import PageLayout from '../components/PageLayout.js';
import styled, { useTheme } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { async } from 'q';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import API from "../utils/API.js";
import { setSelectionRange } from '@testing-library/user-event/dist/utils/index.js';
import Swal from "sweetalert2";

registerLocale('ko', ko);
setDefaultLocale('ko');

const SignupForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
 

  label {
    margin-bottom: 8px;
    margin-top: 20px;
    text-align: left;
    width: 100%;
    box-sizing: border-box;
    font-weight: bold;
  }

  
  input {
    padding: 8px;
    box-sizing: border-box;
    width: 70%;
  }

  .container {
    display: flex;
    align-items: center;
    width: 100%;
    margin-bottom: -10px;
    margin-top: -15px;
  }


  .authentication-button {
    background-color: #f5f7fa;
    color: #000080;
    padding: 5px 8px;
    cursor: pointer;
    border: none;
    font-weight: bold;
    font-size: 14px;
    width: 65px;
    height: 32px;
    border-radius: 5px;
    margin-bottom: 20px;
    margin-top: 15px;
    margin-left: 35px;
  }


  .doublecheck-button {
    background-color: #f5f7fa;
    color: #000080;
    padding: 5px 8px;
    cursor: pointer;
    border: none;
    font-weight: bold;
    font-size: 14px;
    width: 160px;
    height: 30px;
    border-radius: 5px;
    margin-bottom: 20px;
    margin-top: 15px;
    margin-left: 35px;
  }

  .password-condition {
    color: red;
    font-size: 14px;
    margin-top: 5px;
  }
`;

const CenteredContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
`;


const SignupFormContainer = styled.div`
  background-color: #ffffff; 
  padding: 35px 40px; 
  border-radius: 20px;
  box-shadow: 
  0 0 10px rgba(0, 0, 0, 0.1);
  box-sizing: border-box; 
  margin: 20px 0;
  width: 540px;
`;

const AgreementContainer = styled.div`
  display: flex;
  align-items: center;

  label {
    white-space: nowrap;
    margin-top: 6px;
    margin-left: 5px;
  }
`

const SignUpButton = styled.button`
  background-color: #000080;
  color: #fff;
  width: 440px;
  height: 50px;
  padding: 10px;
  cursor: pointer;
  border: none;
  margin-top: 20px;
`;


const SignUpPage = () => {
  const navigate = useNavigate();
  const [selectedDate, setSelectedDate] = useState(new Date());
  
  const [passwordCondition, setPasswordCondition] = useState('');
  const [passwordMatch, setPasswordMatch] = useState(true);
  const [idAvailabilityMessage, setIdAvailabilityMessage] = useState('');

  const [inputValue, setInputValue] = useState({
    userNumber: "",
    employeeNumber: "",
    department: "",
    userId: "",
    password: "",
    userName: "",
    birthDate: "",
    userEmail: "",
    userPhoneNumber: ""
  });

  const inputChangeHandler = (e, name) => {
    const { value } = e.target;

    // 비밀번호 조건 체크
    if (name === 'password') {
      const isPasswordValid = checkPasswordConditions(value);
      setPasswordCondition(isPasswordValid ? '' : '비밀번호는 영어 소문자, 대문자, 특수문자 1개 이상 포함, 8자 이상이어야 합니다.');
    }

    setInputValue((prevInputValue) => ({
      ...prevInputValue,
      [name]: value,
    }));

    // 비밀번호 확인 체크
    if (name === 'passwordConfirm') {
      setPasswordMatch(value === inputValue.password);
    }
  };

  const checkPasswordConditions = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}|:"<>?])[A-Za-z\d!@#$%^&*()_+{}|:"<>?]{8,}$/;

    return passwordRegex.test(password);
  };
  
  const checkDuplicateId = async () => {
    try {
      const response = await API.post('/users/check-duplicate-id', { userId: inputValue.userId });
  
      if (response.data.available) {
        // 아이디 사용 가능한 경우
        setIdAvailabilityMessage('사용 가능한 아이디입니다.');
        // 기타 필요한 로직 추가 가능
      } else {
        // 아이디 중복된 경우
        setIdAvailabilityMessage('이미 존재하는 아이디입니다.');
        // 기타 필요한 로직 추가 가능
      }
    } catch (error) {
      console.error('서버와의 통신 중 오류 발생', error);
    }
  };

  const handleCheckDuplicateId = () => {
    checkDuplicateId();
  }

  const handleSignUp = async (e) => {
    e.preventDefault()

    console.log("회원가입 버튼 클릭");

    const data = {
      userNumber:null,
      employeeNumber: inputValue.employeeNumber,
      department: inputValue.department,
      userId: inputValue.userId,
      password: inputValue.password,
      userName: inputValue.userName,
      birthDate: inputValue.birthDate,
      userEmail: inputValue.userEmail,
      userPhoneNumber: inputValue.userPhoneNumber, 
    };
    console.log(data);
    try {
      console.log("서버응답*******************8");
      const response = await API.post('/users/signup', data);
      
      Swal.fire({
        title: '회원가입 성공!',
        icon: 'success',
        confirmButtonColor: '#000080',
        confirmButtonText: '확인',
      }).then((result) => {
        if (result.isConfirmed) {
          navigate('/');
        }
      });
    }
    catch (error) {
      console.error("서버와의 통신 중 오류 발생", error);
    }
  };
  
  return (
    <PageLayout>
        <CenteredContainer>
            <h1 style={{fontSize: '40px', marginBottom: '25px', marginTop: '55px', fontWeight: 'bold'}}>가입하기</h1>
            <SignupFormContainer>
                <SignupForm onSubmit={handleSignUp}>
                  <label>사업자등록번호</label>
                  <div className='container'>
                    <input 
                      type='text' 
                      required 
                      name='employeeNumber'
                      value={inputValue.employeeNumber}
                      onChange={(e) => inputChangeHandler(e, 'employeeNumber')}
                      style={{width: 322, marginBottom: '15px', marginTop: '15px', marginLeft: '-11.3px' }}
                    />
                    <button className='authentication-button' type='button'>인증</button>
                  </div>

                  <label>회사명</label>
                  <input 
                    type='text' 
                    required 
                    name='department'
                    value={inputValue.department}
                    onChange={(e) => inputChangeHandler(e, 'department')}
                    style={{marginBottom: '8px'}}
                  />
                  
                  <label> 아이디</label>
                  <div className='container'>
                    <input 
                      type='id' 
                      required 
                      name='userId'
                      value={inputValue.userId}
                      onChange={(e) => inputChangeHandler(e, 'userId')}
                      style={{width: 600, marginBottom: '18px', marginTop: '15px', marginLeft: '-11.3px' }}
                    />
                    <button className='doublecheck-button' type='button' onClick={handleCheckDuplicateId}>중복확인</button>
                  </div>
                  { idAvailabilityMessage && <div style={{ color: idAvailabilityMessage.includes('가능') ? 'blue' : 'red' }}>{idAvailabilityMessage}</div> }

                  <label>비밀번호</label>
                  <input 
                    type="password" 
                    required 
                    name="password"
                    value={inputValue.password}
                    onChange={(e) => inputChangeHandler(e, 'password')}
                    style={{marginBottom: '8px'}}
                  />
                  {passwordCondition && <div className="password-condition">{passwordCondition}</div>}
                        
                  <label>비밀번호 확인</label>
                  <input 
                    type="password" 
                    required 
                    name='passwordConfirm'
                    value={inputValue.passwordConfirm}
                    onChange={(e) => inputChangeHandler(e, 'passwordConfirm')}
                    style={{marginBottom: '8px'}}
                  />
                  {!passwordMatch && <div className="password-condition">비밀번호가 일치하지 않습니다.</div>}
                        
                  <label>이름</label>
                  <input 
                    type='text'
                    required
                    name='userName' 
                    value={inputValue.name}
                    onChange={(e) => inputChangeHandler(e, 'userName')}
                  />

                  <label>생년월일</label>
                  <DatePicker
                    selected={selectedDate}
                    onChange={(date) => setSelectedDate(date)}
                    dateFormat='yyyy-MM-dd'
                    placeholderText='"YYYY-MM-DD'
                    required
                    popperPlacement='right'
                    showPopperArrow={false}
                    showYearDropdown
                    showMonthDropdown
                  />

                  <label>이메일</label>
                    <input 
                      type='email' 
                      required 
                      name='userEmail'
                      value={inputValue.email}
                      onChange={(e) => inputChangeHandler(e, 'userEmail')}
                    />

                  <label>휴대전화</label>
                    <input 
                      type='text'  
                      required 
                      name='userPhoneNumber'
                      value={inputValue.phoneNumber}
                      onChange={(e) => inputChangeHandler(e, 'userPhoneNumber')}
                    />
                  
                  <div style={{marginBottom: '30px'}}></div>
                  
                  <AgreementContainer>
                    <input type='checkbox' id='agree'/>
                    <label htmlFor='agree'>개인정보 수집 동의</label>
                  </AgreementContainer>
                 
                  <SignUpButton type='submit'>가입하기</SignUpButton>
                </SignupForm>
            </SignupFormContainer>
        </CenteredContainer>
    </PageLayout>
  );
};

export default SignUpPage;