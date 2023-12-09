package com.abnormal.detection.repository.video;

import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.video.Video;

import java.util.List;

public interface VideoRepository {
    Video createVideo(Video video);

    Video getVideoById(Long videoId);

    List<Video> getVideoByName(String videoName);

    List<Video> getVideoByExtension(String extension);

    List<Video> getVideoByProtocol(String protocol);

    Video saveVideoData(Video videoMetadata);

    void deleteVideoMetadata(Long videoId);
}
