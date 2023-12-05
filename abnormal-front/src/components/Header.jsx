import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Button from '@mui/material/Button';
import styled from 'styled-components';

const HeaderContainer = styled.div`
  padding: 1rem 2rem;
  background-color: white;
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
`;

const Header = () => {
  const { user, logout } = useAuth();

  return (
    <HeaderContainer>
      {/* 다른 헤더 컨텐츠 */}
      <MenuBarButtons>
        {user ? (
          <>
            <span>{user.username}</span>
            <Button onClick={logout}>로그아웃</Button>
          </>
        ) : (
          <>
            <Link to="/login">
              <Button variant="outlined">로그인</Button>
            </Link>
            <Link to="/signup">
              <Button variant="contained">가입하기</Button>
            </Link>
          </>
        )}
      </MenuBarButtons>
    </HeaderContainer>
  );
};

export default Header;