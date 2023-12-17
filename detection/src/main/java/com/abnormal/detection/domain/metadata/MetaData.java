package com.abnormal.detection.domain.metadata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.util.Date;

@Entity
@Getter
@Setter
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long metaDataId;

    //@Version
    Date foundTime;

    //@Version
    Date entityFoundTime;

    @Version
    Long cctvId;

    EntityType type;
    AbnormalType abnormalType;
    Quality quality;

    //@Version
    Long videoId;

    //@Version
    Long photoId;

    @Column(length = 1000000000)
    String base64Image;

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
