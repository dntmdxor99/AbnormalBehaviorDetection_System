import React from "react";
import styled from "styled-components";
import { useRecoilValue, useRecoilState } from "recoil";
import abnormalBehaviorState from "../recoil/abnormalBehaviorState";
import resultState from "../recoil/resultState";
import MediaCard from "../components/Card";

const Rectangle = styled.div`
  width: 400px;
  height: 100%;
  background: #f5f7fa;
  position: absolute;
  left: 0;
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
  font-size: 20px;
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
  const [selectedAbnormalBehaviors, setSelectedAbnormalBehaviors] =
    useRecoilState(abnormalBehaviorState);
  const recoilResultState = useRecoilValue(resultState);

  console.log(recoilResultState);

  // {
  //     "metaDataId": 1,
  //     "foundTime": "2020-08-06T03:04:00.000+00:00",
  //     "entityFoundTime": "2020-08-06T03:05:00.000+00:00",
  //     "cctvId": 1,
  //     "type": "PERSON",
  //     "abnormalType": "fight",
  //     "quality": "HIGH",
  //     "videoId": 1,
  //     "photoId": 1
  // }

  return (
    <div>
      <Rectangle>
        <Box>
          <Types>
            검색 세부 정보
            <Contents>위치</Contents>
            <Contents>구간</Contents>
          </Types>
          <Types>
            이상 행동 유형
            <Contents>
              <div>
                {selectedAbnormalBehaviors.map((behavior, index) => (
                  <SelectionButton key={index}>{behavior}</SelectionButton>
                ))}
              </div>
            </Contents>
          </Types>
          <Types>CCTV</Types>
          {recoilResultState.map(
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
                />
              );
            }
          )}
        </Box>
      </Rectangle>
    </div>
  );
};

export default ResultPage;
