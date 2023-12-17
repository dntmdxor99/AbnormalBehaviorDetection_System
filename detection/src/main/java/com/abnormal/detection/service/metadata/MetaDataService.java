package com.abnormal.detection.service.metadata;


import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import com.abnormal.detection.repository.metadata.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MetaDataService {

    private final MetaDataRepository metaDataRepository;

    @Autowired
    public MetaDataService(MetaDataRepository metaDataRepository) {
        this.metaDataRepository = metaDataRepository;
    }

    @Transactional
    public MetaData createMetaData(MetaData metaData) {
        return metaDataRepository.createMetaData(metaData);
    }

    public List<MetaData> getAllMetaDatas() {
        return metaDataRepository.getAllMetaDatas();
    }



    public MetaData getMetadataById(Long metaDataId) {
        return metaDataRepository.getMetadataById(metaDataId);
    }

    public List<MetaData> getMetadataByCctvId(Long cctvId) {
        return metaDataRepository.getMetadataByCctvId(cctvId);
    }
//

//
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

    //비관적 락 제거(낙관적 락 사용)
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public MetaData updateMetadata(MetaData metaData) {
        return metaDataRepository.updateMetadata(metaData);
    }

    public void deleteMetadata(Long metaDataId) {
        metaDataRepository.deleteMetadata(metaDataId);
    }

    public void updateMetadataInfo(Long metaDataId, MetaData updatedMetadata) {
        metaDataRepository.updateMetadataInfo(metaDataId, updatedMetadata);
    }

    public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
        return metaDataRepository.getMetadataByDateRange(startDate, endDate);
    }

    public List<MetaData> searchMetaDatasByOptions(Date foundTime, Date entityFoundTime, Long cctvId, AbnormalType abnormalType) {
        return metaDataRepository.searchMetaDatasByOptions(foundTime, entityFoundTime, cctvId, abnormalType);
    }

    public List<MetaData> searchLegendByOptions(Date startDate, Date endDate, Long cctvId, AbnormalType abnormalType) {
        return metaDataRepository.searchLegendByOptions(startDate, endDate, cctvId, abnormalType);
    }
}
