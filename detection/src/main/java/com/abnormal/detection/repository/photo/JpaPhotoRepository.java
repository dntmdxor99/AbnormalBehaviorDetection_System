package com.abnormal.detection.repository.photo;

import com.abnormal.detection.domain.photo.Photo;

import java.util.List;

public class JpaPhotoRepository implements PhotoRepository{
    @Override
    public Photo getPhotoById(Long photoId) {
        return null;
    }

    @Override
    public List<Photo> getPhotosByVideoId(Long videoId) {
        return null;
    }

    @Override
    public List<Photo> getPhotosByMetadataId(Long metaDataId) {
        return null;
    }

    @Override
    public List<Photo> getPhotosByPathUrl(String pathUrl) {
        return null;
    }

    @Override
    public Photo savePhoto(Photo photo) {
        return null;
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        return null;
    }

    @Override
    public void deletePhoto(Long photoId) {

    }
}
