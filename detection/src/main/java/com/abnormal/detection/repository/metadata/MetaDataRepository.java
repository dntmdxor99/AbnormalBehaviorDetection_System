package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;


import java.util.Date;
import java.util.List;

public interface MetaDataRepository {
    MetaData createMetaData(MetaData metaData);
    List<MetaData> getAllMetaDatas();

    MetaData getMetadataById(Long metaDataId);

    List<MetaData> getMetadataByCctvId(Long cctvId);

    List<MetaData> getMetadataByDateRange(Date startDate, Date endDate);

    List<MetaData> getMetadataByEntityType(EntityType type);

    List<MetaData> getMetadataByAbnormalType(AbnormalType abnormalType);

    List<MetaData> getMetadataByQuality(Quality quality);

    List<MetaData> getMetadataByVideoId(Long videoId);

    List<MetaData> getMetadataByPhotoId(Long photoId);

    MetaData saveMetadata(MetaData metadata);

    MetaData updateMetadata(MetaData metadata);

    void deleteMetadata(Long metaDataId);

    void updateMetadataInfo(Long metaDataId, MetaData updatedMetadata);
//f실제로 객체가 언제로 이상행동검출
    //en 해당 비디오 내에서 몇번째 프레임인지
    List<MetaData> searchMetaDatasByOptions(Date foundTime, Date entityFoundTime, Long cctvId,  AbnormalType abnormalType);

}
