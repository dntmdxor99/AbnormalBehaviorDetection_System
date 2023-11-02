package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.Quality;
import org.hibernate.boot.Metadata;

import java.util.Date;
import java.util.List;

public interface MetaDataRepository {
    Metadata getMetadataById(Long metaDataId);

    List<Metadata> getMetadataByCctvId(Long cctvId);

    List<Metadata> getMetadataByDateRange(Date startDate, Date endDate);

    List<Metadata> getMetadataByEntityType(EntityType type);

    List<Metadata> getMetadataByAbnormalType(AbnormalType abnormalType);

    List<Metadata> getMetadataByQuality(Quality quality);

    List<Metadata> getMetadataByVideoId(Long videoId);

    List<Metadata> getMetadataByPhotoId(Long photoId);

    Metadata saveMetadata(Metadata metadata);

    Metadata updateMetadata(Metadata metadata);

    void deleteMetadata(Long metaDataId);

    void updateMetadataInfo(Long metaDataId, Metadata updatedMetadata);
}
