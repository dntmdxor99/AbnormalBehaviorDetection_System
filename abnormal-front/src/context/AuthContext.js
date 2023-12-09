import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';
import API from '../utils/API';


const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const login = (userData) => {
    setUser({userData: userData.username});
    localStorage.setItem('login-token', userData.token);
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
    } catch (error) {
      console.error('로그아웃 중 에러 발생', error);
    }
  };


  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('login-token');
      if (token) {
        try {
          const response = await API.get('/users/login', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });
          setUser({ username: response.data.username, token });
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