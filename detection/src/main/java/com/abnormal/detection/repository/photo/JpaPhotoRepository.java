package com.abnormal.detection.repository.photo;

import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.photo.Photo;
import com.abnormal.detection.domain.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JpaPhotoRepository implements PhotoRepository{

    private final EntityManager em;

    public JpaPhotoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Photo createPhoto(Photo photo) {
        // 고유 ID 생성 및 설정
        photo.setPhotoId(null); // ID는 자동 생성
        em.persist(photo);
        return photo;
    }

    @Override
    public Photo getPhotoById(Long photoId) {
        return em.find(Photo.class, photoId);
    }

    @Override
    public List<Photo> getPhotosByVideoId(Long videoId) {
        return em.createQuery("SELECT p FROM Photo p WHERE p.videoId = :videoId", Photo.class)
                .setParameter("videoId", videoId).getResultList();
    }

    @Override
    public List<Photo> getPhotosByMetadataId(Long metaDataId) {
        return em.createQuery("SELECT p FROM Photo p WHERE p.metaDataId = :metaDataId", Photo.class)
                .setParameter("metaDataId", metaDataId).getResultList();
    }

    @Override
    public List<Photo> getPhotosByPathUrl(String pathUrl) {
        return null;
    }

    @Override
    public Photo savePhoto(Photo photo) {
        em.persist(photo);
        return photo;
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        Photo toUpdate = em.find(Photo.class, photo.getPhotoId());

        if (toUpdate != null) {
            toUpdate.setVideoId(photo.getVideoId());
            toUpdate.setMetaDataId(photo.getMetaDataId());
            toUpdate.setPathUrl(photo.getPathUrl());

            em.merge(toUpdate);
        }

        return toUpdate;
    }

    @Override
    public void deletePhoto(Long photoId) {
        Photo photo = em.find(Photo.class, photoId);
        if (photo != null) {
            em.remove(photo);
        }
    }
}
