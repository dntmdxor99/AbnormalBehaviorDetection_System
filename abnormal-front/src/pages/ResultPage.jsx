import { React } from "react";
import styled from "styled-components";
import { useRecoilValue } from "recoil";
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

  const abnormalTypes = recoilResultState.map((item) => item.abnormalType);
  const abnormalTypeKorean = {
    fight: "싸움",
    assault: "폭행",
    drunken: "주취행동",
    swoon: "기절",
    kidnap: "납치",
  };

  const uniqueAbnormalTypes = [...new Set(abnormalTypes)];
  console.log(uniqueAbnormalTypes);

  // const { entityFoundTime, foundTime } = recoilResultState[0];
  // const findDate = new Date(foundTime).toISOString().split("T")[0];

  return (
    <div>
      <Rectangle>
        <Box>
          <Types>
            검색 세부 정보
            <Contents>
              발견 날짜 :
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
