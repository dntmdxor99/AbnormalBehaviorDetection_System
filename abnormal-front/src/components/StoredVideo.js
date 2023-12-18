import React, { useEffect, useRef } from "react";
import styled from "styled-components";
import cancelIcon from '../assets/img/cancel.png';


const PopupContainer = styled.div`
  position: fixed;
  top: 55%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  z-index: 1000;
  width: 900px;
  height: 500px;
  margin: 0;
`;

const Header = styled.div`
  background-color: #DDDDDD;
  color: #000000;
  margin-bottom: -25px;
  width: 100%;
  height: 50px;
  box-sizing: border-box;
`

const Body = styled.div`
  width: 100%;
  height: 100%;
`

const CloseButton = styled.button`
  position: absolute;
  right: 10px;
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

const CloseIcon = styled.img`
  width: 30px;
  height: 30px;
`

const VideoContainer = styled.div`
  width: 100%;
  height: 95%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  margin-top: 5px;
`;

const StoredVideo = ({ onClose }) => {
    
    return (
        <PopupContainer>  
        <Header>
            <CloseButton onClick={onClose}>
            <CloseIcon src={cancelIcon} alt="Close" />
            </CloseButton>
        </Header>
        <Body>
            <VideoContainer>
                <video controls style={{width: "100%", height: "100%", objectFit: 'cover'}} autoPlay>
                    <source src="http://172.20.56.218:8080/stream" type="video/mp4" />
                </video>
            </VideoContainer>
        </Body> 
        </PopupContainer>
    );
};


export default StoredVideo;