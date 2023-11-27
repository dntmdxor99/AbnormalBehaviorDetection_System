import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";

import '../App.css';
import { Map } from 'react-kakao-maps-sdk';
import useWindowSize from "../hooks/useWindowSize";
import useUserPosition from "../hooks/useUserPosition";
import InsideMap from "../components/InsideMap";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const SearchPage = () => {
  const windowSize = useWindowSize();
  const userPosition = useUserPosition();

  useEffect(() => {
    console.log(userPosition);
  }, []);

  return (
    <PageLayout>
      <Frame>
        <div className="search">
          <div className="search-main">
            <h1 className="search-main-title"></h1>
            <Map
              center={{ lat: 33.5563, lng: 126.79581 }}
              style={{
                width: `${windowSize.width}px`,
                height: `${windowSize.height}px`
              }}
              level={3}
            >
              <InsideMap />
            </Map>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default SearchPage;
