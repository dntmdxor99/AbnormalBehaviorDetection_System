import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Button from '@mui/material/Button';
import styled, {createGlobalStyle} from 'styled-components';


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
  flex-shrink: 0;
`;


const MenuItem = styled.h4`
  margin-left: 50px;
  margin-right: 40px;
  cursor: pointer;
  flex-shrink: 0;
`;


const MenuBarButtons = styled.div`
  display: flex;
  align-items: center;

  span {
    margin-right: 10px;
  }

  button {
    margin-left: 10px;
  }

  a {
    text-decoration: none;
  }
`;


const StyledLink = styled(Link)`
  color: black;
  text-decoration: none;
  font-weight: bold
`;


const Header = () => {
  const { user, logout } = useAuth();

  return (
    <HeaderContainer>
      <MenuBar>
          <MenuItem>
            <StyledLink to="/" className="menu-home-item">
              7팀
            </StyledLink>
          </MenuItem>
          <MenuItem>
            <StyledLink to="/abnormal" className="menu-bar-item">
              이상행동
            </StyledLink>
          </MenuItem>
          <MenuItem>
            <StyledLink to="/cctv" className="menu-bar-item">
              CCTV
            </StyledLink>
          </MenuItem>
          <MenuItem>
            <StyledLink to="/ask" className="menu-bar-item">
              문의하기
            </StyledLink>
          </MenuItem>
      </MenuBar>

      <MenuBarButtons>
        {user ? (
          <>
            <span>{user.username}</span>
            <Button onClick={logout}>로그아웃</Button>
          </>
        ) : (
          <>
            <Link to="/login" style={loginButtonStyle}>
              로그인
            </Link>
            <Link to="/signup" style={signButtonStyle}>
              가입하기
            </Link>
          </>
        )}
      </MenuBarButtons>
    </HeaderContainer>
  );
};

export default Header;