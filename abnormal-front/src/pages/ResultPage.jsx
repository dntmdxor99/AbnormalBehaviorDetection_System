import React from 'react'
import styled from "styled-components";
import { useRecoilValue } from "recoil";
import resultState from "../recoil/resultState";
import MediaCard from "../components/Card";

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
                />
              );
            }
          )}
          </Grid>

      </Container>
    </div>
  );
};

export default ResultPage;
