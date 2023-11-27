import React, { useEffect, useState } from "react";
import Header from "../components/Header.jsx";
import PageLayout from "../components/PageLayout.js";
import styled, { useTheme } from "styled-components";
import API from "../utils/API.js";
import { useNavigate } from 'react-router-dom';


const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;

  input {
    margin-bottom: 40px;
    padding: 8px;
    width: 400px;
    height: 25px;
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
  const navigate = useNavigate();
  const [inputValue, setInputValue] = useState({
    id: '',
    password: '',
  });

  const inputChangeHandler = (e, name) => {
    const {value} = e.target;

    setInputValue((prevInputValue) => ({
      ...prevInputValue,
      [name]: value,
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    console.log("로그인 버튼 클릭됨");

    const data = {
      userId: inputValue.id,
      userPassword: inputValue.password,
    };

    try {
      const response = await API.post('/users/login', data);

      console.log("서버응답", response);

      if (response.status === 200) {
        const {postId} = response.data;
        console.log("로그인 성공!");
        navigate('/')
      }
      else if (response.status === 400) {
        console.log("아이디와 비밀번호가 일치하지않습니다.");
      }
      else {
        console.log("로그인 실패!");
        navigate('/login')
      }
    }
    catch (error) {
      console.error("서버와의 통신 중 오류 발생", error);
    }
  }; 

  return (
    <PageLayout>
      <CenteredContainer>
        <h1
          style={{ fontSize: "55px", marginBottom: "60px", marginTop: "-60px" }}
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

