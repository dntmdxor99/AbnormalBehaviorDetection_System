import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useRecoilValue } from "recoil";
import resultState from "../recoil/resultState";
import MediaCard from "../components/Card";
import StoredVideo from "../components/StoredVideo";
import axios from 'axios';


const Container = styled.div`
  display: flex;
`;

const Rectangle = styled.div`
  width: 400px;
  height: 100%;
  background: #f5f7fa;
  position: absolute;
  left: 0;
`;

const Grid = styled.div`
margin-top: 50px;
margin-left: 50px;
height: 100%;
position: absolute;
left: 400px;
display: flex;
flex-wrap: wrap;
justify-content: space-between;
`
const GridWrapper = styled.div`
  margin-top: 50px;
  margin-bottom: 50px;
`;

const Box = styled.div`
  margin-top: 80px;
  margin-left: 50px;
  margin-right: 40px;
`;

const Types = styled.div`
  font-size: 25px;
  font-style: normal;
  font-weight: 700;
  margin-bottom: 40px;
`;

const Contents = styled.div`
  margin-top: 15px;
  font-size: 16px;
  font-style: normal;
  font-weight: 500;
  line-height: 0.5;
`;

const SelectionButton = styled.div`
  display: inline-block;
  padding: 15px 15px;
  font-size: 15px;
  margin: 4px;
  background-color: #3a3d92;
  color: #ffffff;
  transition: background-color 0.3s;
  text-decoration: none;
  border-radius: 5px;
  // border: 2px solid #3a3d92;
`;

const ResultPage = () => {
  const recoilResultState = useRecoilValue(resultState);
  console.log(recoilResultState);
  const sortedResultState = [...recoilResultState].sort((a, b) => a.videoId - b.videoId);
  console.log("sortedResult --> ",sortedResultState);

  const abnormalTypes = recoilResultState.map((item) => item.abnormalType);
  const abnormalTypeKorean = {
    fight: "싸움",
    assault: "폭행",
    drunken: "주취행동",
    swoon: "기절",
    kidnap: "납치",
  };
  const uniqueAbnormalTypes = [...new Set(abnormalTypes)];

  const foundTimes = recoilResultState.map((item) => item.foundTime);
  const uniqueFoundTimes = [...new Set(foundTimes)];
  const options = { year: "numeric", month: "long", day: "numeric" };
  const startDate = new Date(uniqueFoundTimes[0]).toLocaleDateString(
    "ko-KR",
    options
  );
  const endDate = new Date(
    uniqueFoundTimes[uniqueFoundTimes.length - 1]
  ).toLocaleDateString("ko-KR", options);

  const cctvIds = recoilResultState.map((item) => item.cctvId);
  const uniquecctvIds = [...new Set(cctvIds)];

  const [popupOpen, setPopupOpen] = useState(false);
  const [selectedVideoData, setSelectedVideoData] = useState(null);

  const handleVideoClick = async () => {
    // const startDateObj = new Date(uniqueFoundTimes[0]);
    // const endDateObj = new Date(uniqueFoundTimes[uniqueFoundTimes.length - 1]);

    // const startYear = startDateObj.getFullYear().toString();
    // const startMonth = (startDateObj.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1, 두 자리로 패딩
    // const start_Date = startDateObj.getDate().toString().padStart(2, '0'); // 두 자리로 패딩
    // const startHour = startDateObj.getHours().toString().padStart(2, '0'); // 두 자리로 패딩
    // const startMin = startDateObj.getMinutes().toString().padStart(2, '0'); // 두 자리로 패딩

    // const endYear = endDateObj.getFullYear().toString();
    // const endMonth = (endDateObj.getMonth() + 1).toString().padStart(2, '0');
    // const end_Date = endDateObj.getDate().toString().padStart(2, '0');
    // const endHour = endDateObj.getHours().toString().padStart(2, '0');
    // const endMin = endDateObj.getMinutes().toString().padStart(2, '0');

    const requestData = {
      ip: "118.45.212.161",
      channel: "1",
      startYear: '2023',
      startMonth: '12',
      startDate: '10',
      startHour: '20',
      startMin: '01',
      endYear: '2023',
      endMonth: '12',
      endDate: '10',
      endHour: '20',
      endMin: '06',
    };

    console.log(requestData);

    try {
      const response = await axios.post('http://172.20.56.247:8080/videos', requestData, {
        responseType: 'arraybuffer',
      });
      
      // const blob = new Blob([response.data], {type: 'video/mp4'});

      // const videoFile = new File([blob], 'downloaded_video.mp4', {type: 'video/mp4'});

      // const a = document.createElement('a');
      // a.href = URL.createObjectURL(videoFile);
      // a.download = 'downloaded_video.mp4';

      // document.body.appendChild(a);
      // a.click();
      // document.body.removeChild(a);

      setPopupOpen(true);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const closePopup = () => {
    setPopupOpen(false);
  };


  return (
    <div>
       <Container>
      <Rectangle>
        <Box>
          <Types>
            발견된 날짜
            <Contents>
              {startDate} 부터 {endDate} 까지
            </Contents>
          </Types>
          <Types>
            발견된 이상 행동
            <Contents>
              <div>
                {uniqueAbnormalTypes.map((type, index) => (
                  <SelectionButton key={index}>
                    {abnormalTypeKorean[type]}
                  </SelectionButton>
                ))}
              </div>
            </Contents>
          </Types>
          <Types>
            CCTV | {uniquecctvIds.length}개
            <Contents>CCTV ID : {uniquecctvIds.join(', ')}</Contents>
          </Types>
        </Box>
      </Rectangle>
      <Grid>
      {sortedResultState.map(
            ({
              metaDataId,
              foundTime,
              entityFoundTime,
              cctvId,
              type,
              abnormalType,
              quality,
              videoId,
              photoId,
              base64Image,
            }) => {
              return (
                <MediaCard
                  key={metaDataId}
                  metaDataId={metaDataId}
                  foundTime={foundTime}
                  entityFoundTime={entityFoundTime}
                  cctvId={cctvId}
                  type={type}
                  abnormalType={abnormalType}
                  quality={quality}
                  videoId={videoId}
                  photoId={photoId}
                  base64Image={base64Image}
                  onClick={handleVideoClick}
                />
              );
            }
          )}
          </Grid>

          {popupOpen && <StoredVideo onClose={closePopup} />}
      </Container>
    </div>
  );
};

export default ResultPage;
