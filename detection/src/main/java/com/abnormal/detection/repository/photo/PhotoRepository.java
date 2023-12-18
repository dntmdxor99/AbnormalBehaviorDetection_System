package com.abnormal.detection.repository.photo;

import com.abnormal.detection.domain.photo.Photo;
import com.abnormal.detection.domain.user.User;

import java.util.List;

public interface PhotoRepository {
    Photo createPhoto(Photo photo);

    Photo getPhotoById(Long photoId);

    List<Photo> getPhotosByVideoId(Long videoId);

    List<Photo> getPhotosByMetadataId(Long metaDataId);

    List<Photo> getPhotosByPathUrl(String pathUrl);

    Photo savePhoto(Photo photo);

    Photo updatePhoto(Photo photo);

    void deletePhoto(Long photoId);

}
