import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

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
}) {
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        sx={{ height: 140 }}
        image="/static/images/cards/contemplative-reptile.jpg"
        title="green iguana"
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {metaDataId}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          foundTime: {foundTime}
          <br />
          entityFoundTime: {entityFoundTime}
          <br />
          type: {type}
          <br />
          abnormalType: {abnormalType}
          <br />
          quality: {quality}
          <br />
          videoId: {videoId}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">{abnormalType}</Button>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
  );
}
