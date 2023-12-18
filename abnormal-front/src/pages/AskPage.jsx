import React, {useState} from 'react';
import Header from '../components/Header.jsx';
import PageLayout from '../components/PageLayout.js';
import styled, { useTheme } from 'styled-components';


const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const AskPage = () => {
    return (
        <PageLayout>
            <Frame>
                <div className="ask">
                <div className="ask-main">
                    <h1 className="ask-main-title">문의하기</h1>
                </div>
                </div>
            </Frame>
        </PageLayout>
    )
}

export default AskPage;