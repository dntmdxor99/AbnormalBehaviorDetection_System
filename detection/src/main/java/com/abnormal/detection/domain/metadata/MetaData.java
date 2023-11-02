package com.abnormal.detection.domain.metadata;

import java.util.Date;

public class MetaData {
    Long metaDataId;
    Date foundTime;
    Date entityFoundTIme;
    Long cctvId;
    EntityType type;
    AbnormalType abnormalType;
    Quality quality;
    Long videoId;
    Long photoId;


    public Long getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(Long metaDataId) {
        this.metaDataId = metaDataId;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public Date getEntityFoundTIme() {
        return entityFoundTIme;
    }

    public void setEntityFoundTIme(Date entityFoundTIme) {
        this.entityFoundTIme = entityFoundTIme;
    }

    public Long getCctvId() {
        return cctvId;
    }

    public void setCctvId(Long cctvId) {
        this.cctvId = cctvId;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public AbnormalType getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(AbnormalType abnormalType) {
        this.abnormalType = abnormalType;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
}
