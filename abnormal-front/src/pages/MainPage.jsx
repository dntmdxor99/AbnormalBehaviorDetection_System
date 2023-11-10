import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';

const MainPage = () => {
  return (
    <PageLayout>
      <Header>
          <div className='APP'>
          <div className='menu-bar'>
            <h4 className='item'>7팀</h4>
            <h4 className='item'>이상행동</h4>
            <h4 className='item'>CCTV</h4>
            <h4 className='item'>문의하기</h4>
          </div>
        </div>
        </Header>
    </PageLayout>
  )
};

export default MainPage;
