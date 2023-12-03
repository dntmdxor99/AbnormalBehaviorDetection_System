package com.abnormal.detection.domain.cctv;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cctv {
//이름 주소 360도 비디오사이즈 필터링 검색기능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cctvId;
    private String cctvName;
    private String location;
    private Float latitude;// 위도
    private Float longitude;// 경도
    private Boolean is360Degree;//true,f로 초기화
    private String channel;//protocol->채널
    private String videoSize;

    public Cctv() {

    }
/*
    public Long getCctvId() {
        return cctvId;
    }

    public void setCctvId(Long cctvId) {
        this.cctvId = cctvId;
    }

    public String getCctvName() {
        return cctvName;
    }

    public void setCctvName(String cctvName) {
        this.cctvName = cctvName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIs360Degree() {
        return is360Degree;
    }

    public void setIs360Degree(Boolean is360Degree) {
        this.is360Degree = is360Degree;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

 */
}
