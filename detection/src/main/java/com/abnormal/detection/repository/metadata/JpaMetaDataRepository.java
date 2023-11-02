package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.Quality;
import org.hibernate.boot.Metadata;

import java.util.Date;
import java.util.List;

public class JpaMetaDataRepository implements MetaDataRepository{

    @Override
    public Metadata getMetadataById(Long metaDataId) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByCctvId(Long cctvId) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByDateRange(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByEntityType(EntityType type) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByAbnormalType(AbnormalType abnormalType) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByQuality(Quality quality) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByVideoId(Long videoId) {
        return null;
    }

    @Override
    public List<Metadata> getMetadataByPhotoId(Long photoId) {
        return null;
    }

    @Override
    public Metadata saveMetadata(Metadata metadata) {
        return null;
    }

    @Override
    public Metadata updateMetadata(Metadata metadata) {
        return null;
    }

    @Override
    public void deleteMetadata(Long metaDataId) {

    }

    @Override
    public void updateMetadataInfo(Long metaDataId, Metadata updatedMetadata) {

    }
}
