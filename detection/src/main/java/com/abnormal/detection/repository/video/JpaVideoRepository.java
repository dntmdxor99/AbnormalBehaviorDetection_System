package com.abnormal.detection.repository.video;

import com.abnormal.detection.domain.video.Video;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaVideoRepository implements VideoRepository{
    EntityManager entityManager;
    @Override
    public Video getVideoById(Long videoId) {
        return null;
    }

    @Override
    public List<Video> getVideoByName(String videoName) {
        return null;
    }

    @Override
    public List<Video> getVideoByExtension(String extension) {
        return null;
    }

    @Override
    public List<Video> getVideoByProtocol(String protocol) {
        return null;
    }

    @Override
    public Video saveVideoData(Video videoMetadata) {
        return null;
    }

    @Override
    public void deleteVideoMetadata(Long videoId) {

    }
}
