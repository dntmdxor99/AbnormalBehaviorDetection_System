import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;
const AbnormalBehaviorPage = () => {
  return (
    <PageLayout>
      <Frame>
        <div className="abnormal">
          <div className="abnormal-main">
            <h1 className="abnormal-main-title">현재 등록된 이상행동</h1>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default AbnormalBehaviorPage;
