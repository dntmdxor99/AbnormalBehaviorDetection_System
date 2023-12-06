package com.abnormal.detection.domain.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long metaDataId;
    Date foundTime;
    Date entityFoundTime;
    Long cctvId;
    EntityType type;
    AbnormalType abnormalType;
    Quality quality;
    Long videoId;
    Long photoId;

    public MetaData() {

    }
    /*
    public void setCctvId(String cctvId) {
        this.cctvId = Long.parseLong(cctvId);
    }

    public void setVideoId(String videoId) {
        this.videoId = Long.parseLong(videoId);
    }

    public void setPhotoId(String photoId) {
        this.photoId = Long.parseLong(photoId);
    }

     */
/*
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

    public Date getEntityFoundTime() {
        return entityFoundTime;
    }

    public void setEntityFoundTime(Date entityFoundTIme) {
        this.entityFoundTime = entityFoundTIme;
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

 */
}
