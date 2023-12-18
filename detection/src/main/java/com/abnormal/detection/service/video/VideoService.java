package com.abnormal.detection.service.video;

import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.video.Video;
import com.abnormal.detection.repository.video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Transactional
    public Video createVideo(Video video) {
        return videoRepository.createVideo(video);
    }

    public Video getVideoById(Long videoId) {
        return videoRepository.getVideoById(videoId);
    }

    public List<Video> getVideoByExtension(String extension) {
        return videoRepository.getVideoByExtension(extension);
    }

    public List<Video> getVideoByProtocol(String protocol) {
        return videoRepository.getVideoByProtocol(protocol);
    }

    public Video saveVideoData(Video videoMetadata) {
        return videoRepository.saveVideoData(videoMetadata);
    }

    public void deleteVideoMetadata(Long videoId) {
        videoRepository.deleteVideoMetadata(videoId);
    }
}
