import axios from 'axios';
import React, {useEffect, useRef, useState} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import styled from 'styled-components';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`
const CctvPage = () => {
    return (
        <PageLayout>
            <Header>
              <div className='menu'>
                <div className='menu-bar'>
                  <h4 className='menu-bar-item'>7팀</h4>
                  <h4 className='menu-bar-item'>이상행동</h4>
                  <h4 className='menu-bar-item'>CCTV</h4>
                  <h4 className='menu-bar-item'>문의하기</h4>
                </div>
              </div>
            </Header>
            <Frame>
              <div className='cctv'>
                <div className='cctv-main'>
                  <h1 className='cctv-main-title'>
                    현재 등록된 CCTV
                  </h1>
                </div>
              </div> 
            </Frame>
        </PageLayout>
    )
};

export default CctvPage;