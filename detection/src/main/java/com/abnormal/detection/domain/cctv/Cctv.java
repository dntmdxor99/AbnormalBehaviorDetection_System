package com.abnormal.detection.domain.cctv;

public class Cctv {

    private Long cctvId;
    private String cctvName;
    private String location;
    private Boolean is360Degree;
    private String protocol;
    private String videoSize;

    public Cctv(Long cctvId, String cctvName, String location, Boolean is360Degree, String protocol, String videoSize) {
        this.cctvId = cctvId;
        this.cctvName = cctvName;
        this.location = location;
        this.is360Degree = is360Degree;
        this.protocol = protocol;
        this.videoSize = videoSize;
    }

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
}
