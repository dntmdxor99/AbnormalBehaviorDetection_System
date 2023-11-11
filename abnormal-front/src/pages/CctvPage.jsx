import axios from 'axios';
import React, {useEffect, useRef, useState} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import styled from 'styled-components';
import PageLayout from '../components/PageLayout';

const Frame = styled.div`
  width: 100vw;
  height: 100vh;


`
const CctvPage = () => {
    return (
        <PageLayout>
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