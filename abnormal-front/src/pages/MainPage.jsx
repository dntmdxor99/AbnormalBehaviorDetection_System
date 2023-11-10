import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import PageLayout from '../components/PageLayout';

const MainPage = () => {
  return (
    <PageLayout>
      <div className='PageLayout'>
        <h1>hi</h1>
      </div>
    </PageLayout>
  )
};

export default MainPage;
