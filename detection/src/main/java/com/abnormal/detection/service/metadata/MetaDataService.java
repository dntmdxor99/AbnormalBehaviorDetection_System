package com.abnormal.detection.service.metadata;


import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import com.abnormal.detection.repository.metadata.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MetaDataService {

    private final MetaDataRepository metaDataRepository;

    @Autowired
    public MetaDataService(MetaDataRepository metaDataRepository) {
        this.metaDataRepository = metaDataRepository;
    }
/*
    public List<MetaData> getAllMetaData() {
        return metaDataRepository.getAllMetaData();
    }

 */



    public MetaData getMetadataById(Long metaDataId) {
        return metaDataRepository.getMetadataById(metaDataId);
    }

    public List<MetaData> getMetadataByCctvId(Long cctvId) {
        return metaDataRepository.getMetadataByCctvId(cctvId);
    }

    public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
        return metaDataRepository.getMetadataByDateRange(startDate, endDate);
    }

    public List<MetaData> getMetadataByEntityType(EntityType type) {
        return metaDataRepository.getMetadataByEntityType(type);
    }

    public List<MetaData> getMetadataByAbnormalType(AbnormalType abnormalType) {
        return metaDataRepository.getMetadataByAbnormalType(abnormalType);
    }

    public List<MetaData> getMetadataByQuality(Quality quality) {
        return metaDataRepository.getMetadataByQuality(quality);
    }

    public List<MetaData> getMetadataByVideoId(Long videoId) {
        return metaDataRepository.getMetadataByVideoId(videoId);
    }

    public List<MetaData> getMetadataByPhotoId(Long photoId) {
        return metaDataRepository.getMetadataByPhotoId(photoId);
    }

    public MetaData saveMetadata(MetaData metaData) {
        return metaDataRepository.saveMetadata(metaData);
    }

    public MetaData updateMetadata(MetaData metaData) {
        return metaDataRepository.updateMetadata(metaData);
    }

    public void deleteMetadata(Long metaDataId) {
        metaDataRepository.deleteMetadata(metaDataId);
    }

    public void updateMetadataInfo(Long metaDataId, MetaData updatedMetadata) {
        metaDataRepository.updateMetadataInfo(metaDataId, updatedMetadata);
    }
}
