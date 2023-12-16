import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import styled from "styled-components";

const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  font-weight: bold;
  color: #ffffff;
  background-color: #fa6f33;
  border: 1px solid #fa6f33;
`;
const abnormalTypeKorean = {
  fight: "싸움",
  assault: "폭행",
  drunken: "주취행동",
  swoon: "기절",
  kidnap: "납치",
};

const qualityKorean = {
  HIGH: "상",
  MIDDLE: "중",
  LOW: "하",
};

// function base64ToBlobUrl(base64Data, contentType) {
//   // Base64 데이터를 바이너리 형식으로 변환
//   const byteCharacters = atob(base64Data);
//   const byteArrays = [];

//   for (let offset = 0; offset < byteCharacters.length; offset += 512) {
//     const slice = byteCharacters.slice(offset, offset + 512);

//     const byteNumbers = new Array(slice.length);
//     for (let i = 0; i < slice.length; i++) {
//       byteNumbers[i] = slice.charCodeAt(i);
//     }

//     const byteArray = new Uint8Array(byteNumbers);
//     byteArrays.push(byteArray);
//   }

//   // Blob 객체를 생성
//   const blob = new Blob(byteArrays, {type: contentType});

//   // Blob URL을 생성
//   const blobUrl = URL.createObjectURL(blob);

//   return blobUrl;
// }

export default function MediaCard({
  metaDataId,
  foundTime,
  base64Image,
  entityFoundTime,
  cctvId,
  type,
  abnormalType,
  quality,
  videoId,
  photoId,
}) {
  const date = new Date(entityFoundTime);
  const formattedDate = date.toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    weekday: "long",
  });



  return (
    <Card sx={{ width: 250, height: 300, marginRight: 2, marginBottom: 2 }}>
      <img
        src={`data:image/png;base64,${base64Image}`}
        width="100px"
        height="100px"
      />
      {/* <CardMedia
        sx={{ height: 100 }}
        image={`data:image/png;base64,${base64Image}`
        }
      /> */}
      <CardContent>
        <Circle>{qualityKorean[quality]}</Circle>
        <br />
        <Typography variant="body2" color="text.secondary">
          {formattedDate}
          <br />• {abnormalTypeKorean[abnormalType]}
          <br />• CCTV ID : {videoId}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small"> 영상 보기</Button>
      </CardActions>
    </Card>
  );
}
