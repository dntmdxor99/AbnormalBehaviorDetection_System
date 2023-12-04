package com.abnormal.detection.controller.photo;

import com.abnormal.detection.domain.photo.Photo;
import com.abnormal.detection.service.photo.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long photoId) {
        Photo photo = photoService.getPhotoById(photoId);
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<Photo>> getPhotosByVideoId(@PathVariable Long videoId) {
        List<Photo> photos = photoService.getPhotosByVideoId(videoId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @GetMapping("/metadata/{metaDataId}")
    public ResponseEntity<List<Photo>> getPhotosByMetadataId(@PathVariable Long metaDataId) {
        List<Photo> photos = photoService.getPhotosByMetadataId(metaDataId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Photo> savePhoto(@RequestBody Photo photo) {
        Photo savedPhoto = photoService.savePhoto(photo);
        return new ResponseEntity<>(savedPhoto, HttpStatus.CREATED);
    }

    @PutMapping("/{photoId}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable Long photoId, @RequestBody Photo photo) {
        Photo updatedPhoto = photoService.updatePhoto(photo);
        return new ResponseEntity<>(updatedPhoto, HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

