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
                      name='use	rPhoneNumber'
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