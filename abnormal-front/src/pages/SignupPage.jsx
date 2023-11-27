import React, {useState} from 'react';
import Header from '../components/Header.jsx';
import PageLayout from '../components/PageLayout.js';
import styled, { useTheme } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { async } from 'q';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import API from "../utils/API.js";


const SignupForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
 

  label {
    margin-bottom: 5px;
    margin-top: 20px;
    text-align: left;
    width: 100%;
    box-sizing: border-box;
    font-weight: bold;
  }


  input {
    padding: 8px;
    box-sizing: border-box;
    width: 80%;
  }


  button {
    background-color: #000080;
    color: #fff;
    width: 440px;
    height: 50px;
    padding: 10px;
    cursor: pointer;
    border: none;
    margin-top: 20px;
  }


  .container {
    display: flex;
    align-items: center;
    width: 100%;
    margin-bottom: -20px;
    margin-top: -15px;
  }


  .authentication-button {
    background-color: #f5f7fa;
    color: #000080;
    padding: 5px 8px;
    cursor: pointer;
    border: none;
    font-weight: bold;
    width: 50px;
    height: 30px;
    border-radius: 5px;
    margin-bottom: 20px;
  }


  .doublecheck-button {
    background-color: #f5f7fa;
    color: #000080;
    padding: 5px 8px;
    cursor: pointer;
    border: none;
    font-weight: bold;
    width: 80px;
    height: 30px;
    border-radius: 5px;
    margin-bottom: 20px;
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
  width: 520px;
`;

const AgreementContainer = styled.div`
  display: flex;
  align-items: center;

  label {
    white-space: nowrap;
    margin-top: -0.1px;
    
  }
`

const SignUpPage = () => {
  const navigate = useNavigate();
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

    setInputValue((prevInputValue) => ({
      ...prevInputValue,
      [name]: value,
    }));
  };

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
      console.log(data);
      console.log("서버응답");
      console.log(response);
      
      navigate('/')
    }
    catch (error) {
      console.error("서버와의 통신 중 오류 발생", error);
    }
  };
  
  return (
    <PageLayout>
        <CenteredContainer>
            <h1 style={{fontSize: '40px', marginBottom: '25px', marginTop: '55px'}}>가입하기</h1>
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
                  />
                    
                  <label> 아이디</label>
                  <div className='container'>
                    <input 
                      type='id' 
                      required 
                      name='userId'
                      value={inputValue.userId}
                      onChange={(e) => inputChangeHandler(e, 'userId')}
                    />
                    <button className='doublecheck-button' type='button'>중복확인</button>
                  </div>
                  
                  <label>비밀번호</label>
                  <input 
                    type="password" 
                    required 
                    // name='password'
                    // value={inputValue.password}
                    // onChange={(e) => inputChangeHandler(e, 'password')}
                  />
                        
                  <label>비밀번호 확인</label>
                  <input 
                    type="password" 
                    required 
                    name='password'
                    value={inputValue.password}
                    onChange={(e) => inputChangeHandler(e, 'password')}
                  />
                        
                  <label>이름</label>
                  <input 
                    type='text'
                    required
                    name='userName' 
                    value={inputValue.name}
                    onChange={(e) => inputChangeHandler(e, 'userName')}
                  />

                  <label>생년월일</label>
                  <input 
                    type='text' 
                    placeholder='YYYY.MM.DD' 
                    required 
                    name='birthDate'
                    value={inputValue.birthDate}
                    onChange={(e) => inputChangeHandler(e, 'birthDate')}
                  />

                  <label>이메일</label>
                  <div className='container'>
                    <input 
                      type='email' 
                      required 
                      name='userEmail'
                      value={inputValue.email}
                      onChange={(e) => inputChangeHandler(e, 'userEmail')}
                    />
                    <button className='doublecheck-button' type='button'>중복확인</button>
                  </div>

                  <label>휴대전화</label>
                  <div className='container'>
                    <input 
                      type='text'  
                      required 
                      name='userPhoneNumber'
                      value={inputValue.phoneNumber}
                      onChange={(e) => inputChangeHandler(e, 'userPhoneNumber')}
                    />
                    <button className='authentication-button' type='button'>인증</button>
                  </div>
                  
                  <div style={{marginBottom: '30px'}}></div>
                  
                  <AgreementContainer>
                    <input type='checkbox' id='agree' />
                    <label htmlFor='agree'>개인정보 수집 동의</label>
                  </AgreementContainer>
                 
                  <button type='submit'>가입하기</button>
                </SignupForm>
            </SignupFormContainer>
        </CenteredContainer>
    </PageLayout>
  );
};

export default SignUpPage;