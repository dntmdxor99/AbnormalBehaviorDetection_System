package com.abnormal.detection.service.photo;

import com.abnormal.detection.domain.photo.Photo;
import com.abnormal.detection.domain.user.User;
import com.abnormal.detection.repository.photo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo createPhoto(Photo photo) {
        return photoRepository.createPhoto(photo);
    }

    public Photo getPhotoById(Long photoId) {
        return photoRepository.getPhotoById(photoId);
    }

    public List<Photo> getPhotosByVideoId(Long videoId) {
        return photoRepository.getPhotosByVideoId(videoId);
    }

    public List<Photo> getPhotosByMetadataId(Long metaDataId) {
        return photoRepository.getPhotosByMetadataId(metaDataId);
    }

    public List<Photo> getPhotosByPathUrl(String pathUrl) {
        return photoRepository.getPhotosByPathUrl(pathUrl);
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.savePhoto(photo);
    }

    public Photo updatePhoto(Photo photo) {
        return photoRepository.updatePhoto(photo);
    }

    public void deletePhoto(Long photoId) {
        photoRepository.deletePhoto(photoId);
    }
}
