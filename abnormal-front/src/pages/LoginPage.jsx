import React, {useState} from 'react';
import Header from '../components/Header.jsx';
import PageLayout from '../components/PageLayout.js';
import styled, { useTheme } from 'styled-components';

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
  const [Id, setId] = useState("");
  const [Password, setPassWord] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    // 로그인 처리 
  };

  return (
    <PageLayout>
        <CenteredContainer>
            <h1 style={{fontSize: '55px', marginBottom: '60px', marginTop: '-60px'}}>로그인</h1>
            <LoginFormContainer>
                <LoginForm onSubmit={handleLogin}>
                    <input type='text' placeholder='아이디' required />
                    <input type='password' placeholder='비밀번호' required />
                    <button type='submit'>로그인</button>
                </LoginForm>
            </LoginFormContainer>
        </CenteredContainer>
    </PageLayout>
  );
};

export default LoginPage;