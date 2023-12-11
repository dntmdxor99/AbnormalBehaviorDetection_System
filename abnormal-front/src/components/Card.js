import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import styled from "styled-components";

export default function MediaCard({
  metaDataId,
  foundTime,
  entityFoundTime,
  cctvId,
  type,
  abnormalType,
  quality,
  videoId,
  photoId,
}) 

{
  return (
<Card sx={{ maxWidth: 250, marginRight: 2, marginBottom: 2 }}>
      <CardMedia
        sx={{ height: 100 }}
        // image="/static/images/cards/contemplative-reptile.jpg"
        // title="green iguana"
      />
      <CardContent>
        <Typography gutterBottom variant="p" component="div">
          {metaDataId}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {/* foundTime: {foundTime}
          <br /> */}
          발견일자 : {entityFoundTime}
          <br />
          {/* type: {type}
          <br /> */}
          이상행동: {abnormalType}
          <br />
          quality: {quality}
          <br />
          videoId: {videoId}
        </Typography>
      </CardContent>
      <CardActions>
        {/* <Button size="small">{abnormalType}</Button> */}
        <Button size="small"> 영상 보기</Button>
      </CardActions>
    </Card>
  );
}
