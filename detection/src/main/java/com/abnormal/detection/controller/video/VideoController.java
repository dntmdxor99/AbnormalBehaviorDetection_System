package com.abnormal.detection.controller.video;

import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.video.Video;
import com.abnormal.detection.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/create")
    public Video createVideo(@RequestBody Video video) {
        return videoService.createVideo(video);
    }

    @GetMapping("/{videoId}")
    public Video getVideoById(@PathVariable Long videoId) {
        return videoService.getVideoById(videoId);
    }

    @GetMapping("/byExtension/{extension}")
    public List<Video> getVideoByExtension(@PathVariable String extension) {
        return videoService.getVideoByExtension(extension);
    }

    @GetMapping("/byProtocol/{protocol}")
    public List<Video> getVideoByProtocol(@PathVariable String protocol) {
        return videoService.getVideoByProtocol(protocol);
    }

    @PostMapping("/save")
    public Video saveVideoData(@RequestBody Video video) {
        return videoService.saveVideoData(video);
    }

    @DeleteMapping("/delete/{videoId}")
    public void deleteVideoMetadata(@PathVariable Long videoId) {
        videoService.deleteVideoMetadata(videoId);
    }
}
