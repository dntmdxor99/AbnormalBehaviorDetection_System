package com.abnormal.detection.repository.video;

import com.abnormal.detection.domain.photo.Photo;
import com.abnormal.detection.domain.video.Video;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JpaVideoRepository implements VideoRepository{
    private final EntityManager em;

    public JpaVideoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Video getVideoById(Long videoId) {
        return em.find(Video.class, videoId);
    }

    @Override
    public List<Video> getVideoByName(String videoName) {
        return null;
    }

    @Override
    public List<Video> getVideoByExtension(String extension) {
        return em.createQuery("SELECT v FROM Video v WHERE v.extension = :extension", Video.class)
                .setParameter("extension", extension)
                .getResultList();
    }

    @Override
    public List<Video> getVideoByProtocol(String protocol) {
        return em.createQuery("SELECT v FROM Video v WHERE v.protocol = :protocol", Video.class)
                .setParameter("protocol", protocol)
                .getResultList();
    }

    @Override
    public Video saveVideoData(Video videoMetadata) {
        em.persist(videoMetadata);
        return videoMetadata;
    }

    @Override
    public void deleteVideoMetadata(Long videoId) {
        Video video = em.find(Video.class, videoId);
        if (video != null) {
            em.remove(video);
        }
    }
}
