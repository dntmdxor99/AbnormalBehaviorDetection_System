import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import PageLayout from "../components/PageLayout";
import API from "../utils/API";

const Frame = styled.div`
  width: 100vw;
  height: 100vh;
`;

const Container = styled.div`
  display: flex;
  background-color: #ffffff;
  padding: 35px 40px;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  justify-content: center;
  margin: 20px 0;
  width: 400px;
  &:not(:first-child) {
    margin-left: 50px;
  }
`;

const Button = styled.div`
  display: inline-block;
  padding: 10px 15px;
  font-size: 15px;
  margin: 4px;
  background-color: #3a3d92;
  color: #ffffff;
  transition: background-color 0.3s;
  text-decoration: none;
  border-radius: 5px;
  // border: 2px solid #3a3d92;
`;

const CctvPage = () => {
  const [cctvData, setCctvData] = useState({
    cctvId: "",
    cctvName: "",
    location: "",
    latitude: "",
    longitude: "",
    is360Degree: "",
    cctvIp: "",
    channel: "",
    videoSize: "",
  });
  const [deleteId, setDeleteId] = useState("");
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
    setDeleteId(event.target.value); // 삭제를 위한 CCTV 아이디 상태 업데이트
  };

  return (
    <PageLayout>
      <Frame>
        <div className="cctv">
          <div className="cctv-main">
            <h1 className="cctv-main-title" style={{marginTop: '-20px', marginBottom: '40px'}}>현재 등록된 CCTV</h1>
            <div style={{ display: "flex" }}>
              <Container>
                <form onSubmit={onSubmit}>
                  <h2 style={{textAlign: "center"}}>CCTV 등록</h2>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    ID:
                    <input
                      type="text"
                      name="cctvId"
                      value={""}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    Name:
                    <input
                      type="text"
                      name="cctvName"
                      value={cctvData.cctvName}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    Location:
                    <input
                      type="text"
                      name="location"
                      value={cctvData.location}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    360Degree:
                    <input
                      type="text"
                      name="is360Degree"
                      value={cctvData.is360Degree}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    CCTV IP:
                    <input
                      type="text"
                      name="cctvIp"
                      value={cctvData.cctvIp}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    Channel:
                    <input
                      type="text"
                      name="channel"
                      value={cctvData.channel}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <label style={{ display: "flex", justifyContent: "space-between" }}>
                    VideoSize:
                    <input
                      type="text"
                      name="videoSize"
                      value={cctvData.videoSize}
                      onChange={onChange}
                      style={{marginBottom: "5px"}}
                    />
                  </label>
                  <Button type="submit">등록</Button>
                </form>
              </Container>
              <Container>
                <form onSubmit={onDelete}>
                  <h2 style={{ textAlign: "center" }}>CCTV 삭제</h2>
                  <label>
                    ID:
                    <input
                      type="text"
                      name="deleteId"
                      value={deleteId}
                      onChange={onIdChange}
                      style={{ marginLeft: "5px" }}
                    />
                  </label>
                  <Button type="button" onClick={onDelete}>
                    삭제
                  </Button>
                </form>
              </Container>
            </div>
          </div>
        </div>
      </Frame>
    </PageLayout>
  );
};

export default CctvPage;
