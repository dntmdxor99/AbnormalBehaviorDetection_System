import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';
import API from '../utils/API';


const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const login = (userData) => {
    setUser({userData: userData.username});
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

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
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