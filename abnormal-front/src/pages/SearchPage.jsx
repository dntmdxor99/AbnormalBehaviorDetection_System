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
const SearchPage = () => {
  return (
    <PageLayout>
      <Frame>
        <div className="search">
          <div className="search-main">
            <h1 className="search-main-title">검색페이지</h1>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default SearchPage;
