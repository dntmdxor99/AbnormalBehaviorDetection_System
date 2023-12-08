import React, { useEffect, useState } from "react";
import Header from "../components/Header.jsx";
import PageLayout from "../components/PageLayout.js";
import styled, { useTheme } from "styled-components";
import API from "../utils/API.js";
import { useNavigate } from 'react-router-dom';
import Modal from '../components/Modal.jsx';
import axios from "axios";
import { useAuth } from "../context/AuthContext.js";


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
  //const onClickHandler = (message) => (` ${message}`);

  const [inputValue, setInputValue] = useState({
    id: '',
    password: '',
  });


  useEffect( () => {
    //checkLoginStatus();
  })

  
  const checkLoginStatus = async () => {
    const token = localStorage.getItem('login-token');

    if (token) {
      try {
        const response = await API.get('/user/info', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        console.log('사용자 정보:', response.data);
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
        
        alert('로그인 성공!');
        navigate('/');
      }
    }
    catch (error) {
      alert('아이디 또는 비밀번호가 일치하지 않습니다.');

      navigate('/login');
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

