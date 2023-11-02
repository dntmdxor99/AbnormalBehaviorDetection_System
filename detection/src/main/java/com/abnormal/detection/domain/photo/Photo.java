package com.abnormal.detection.domain.photo;

public class Photo {
    Long photoId;
    Long videoId;
    Long metaDataId;
    String pathUrl;


    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(Long metaDataId) {
        this.metaDataId = metaDataId;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
}
