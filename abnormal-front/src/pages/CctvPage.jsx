import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import API from "../utils/API";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;
const CctvPage = () => {
  const [cctvData, setCctvData] = useState({
    cctvId: "",
    cctvName: "",
    location: "",
    latitude: "",
    longitude: "",
    is360Degree: "",
    protocol: "",
    videoSize: "",
  });
  const [deleteId, setDeleteId] = useState('');
  const onSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await API.post("/cctvs/create", cctvData);
      console.log(response.data);
    } catch (error) {
      console.error("CCTV 등록에 실패하였습니다.");
    }
  };

  const onDelete = async (event) => {
    event.preventDefault();

    try {
      const response = await API.delete("/cctvs/delete");
      console.log(response.data);
    } catch (error) {
      console.error("CCTV 삭제에 실패하였습니다.");
    }
  };

  const onChange = (event) => {
    setCctvData({
      ...cctvData,
      [event.target.name]: event.target.value,
    });
  };

  const onIdChange = (event) => {
    setDeleteId(event.target.value);   // 삭제를 위한 CCTV 아이디 상태 업데이트
  };

  return (
    <PageLayout>
      <Frame>
        <div className="cctv">
          <div className="cctv-main">
            <h1 className="cctv-main-title">현재 등록된 CCTV</h1>
          </div>
          <form onSubmit={onSubmit}>
          <h2>CCTV 등록</h2>
            <label>
              ID:
              <input
                type="text"
                name="cctvId"
                value={''}
                onChange={onChange}
              />
            </label>
            <label>
              Name:
              <input
                type="text"
                name="cctvName"
                value={cctvData.cctvName}
                onChange={onChange}
              />
            </label>
            <label>
              Location:
              <input
                type="text"
                name="location"
                value={cctvData.location}
                onChange={onChange}
              />
            </label>
            <label>
              360Degree:
              <input
                type="text"
                name="is360Degree"
                value={cctvData.is360Degree}
                onChange={onChange}
              />
            </label>
            <label>
              Protocol:
              <input
                type="text"
                name="protocol"
                value={cctvData.protocol}
                onChange={onChange}
              />
            </label>
            <label>
              VideoSize:
              <input
                type="text"
                name="videoSize"
                value={cctvData.videoSize}
                onChange={onChange}
              />
            </label>
            <button type="submit">등록</button>
            </form>
            <form onSubmit={onDelete}>
            <h2>CCTV 삭제</h2>
            <label>
            ID:
            <input
              type="text"
              name="deleteId"
              value={deleteId}
              onChange={onIdChange}
            />
          </label>
            <button type="button" onClick={onDelete}>
              삭제
            </button>
          </form>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default CctvPage;