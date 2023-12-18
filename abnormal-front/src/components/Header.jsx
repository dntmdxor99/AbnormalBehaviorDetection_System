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
  console.log(user);


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
            <span>아이디 명: 님</span>
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