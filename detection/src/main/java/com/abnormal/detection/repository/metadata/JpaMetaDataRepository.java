package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import jakarta.persistence.EntityManager;
import org.hibernate.boot.Metadata;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaMetaDataRepository implements MetaDataRepository{

    private final EntityManager em;

    public JpaMetaDataRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public MetaData getMetadataById(Long metaDataId) {
        return em.find(MetaData.class, metaDataId);
    }

    @Override
    public List<MetaData> getMetadataByCctvId(Long cctvId) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.cctvId = :cctvId", MetaData.class)
                .setParameter("cctvId", cctvId).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.cctvId >= :startDate and m.cctvId <= :endDate", MetaData.class)
                .setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByEntityType(EntityType type) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.type = :type", MetaData.class)
                .setParameter("type", type).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByAbnormalType(AbnormalType abnormalType) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.abnormalType = :abnormalType", MetaData.class)
                .setParameter("abnormalType", abnormalType).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByQuality(Quality quality) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.quality = :quality", MetaData.class)
                .setParameter("quality", quality).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByVideoId(Long videoId) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.videoId = :videoId", MetaData.class)
                .setParameter("videoId", videoId).getResultList();
    }

    @Override
    public List<MetaData> getMetadataByPhotoId(Long photoId) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.photoId = :photoId", MetaData.class)
                .setParameter("photoId", photoId).getResultList();
    }

    @Override
    public MetaData saveMetadata(MetaData metaData) {
        em.persist(metaData);
        return metaData;
    }

    @Override
    public MetaData updateMetadata(MetaData metaData) {
        MetaData toUpdate = em.find(MetaData.class, metaData.getMetaDataId());

        if (toUpdate != null) {
            toUpdate.setCctvId(metaData.getCctvId());
            toUpdate.setAbnormalType(metaData.getAbnormalType());
            toUpdate.setType(metaData.getType());
            toUpdate.setQuality(metaData.getQuality());
            toUpdate.setVideoId(metaData.getVideoId());
            toUpdate.setFoundTime(metaData.getFoundTime());
            toUpdate.setPhotoId(metaData.getPhotoId());

            toUpdate = em.merge(toUpdate);
        }

        return toUpdate;
    }

    @Override
    public void deleteMetadata(Long metaDataId) {
        MetaData metaData = em.find(MetaData.class, metaDataId);
        if (metaData != null) {
            em.remove(metaData);
        }
    }

    @Override
    public void updateMetadataInfo(Long metaDataId, MetaData updatedMetadata) {
        MetaData toUpdate = em.find(MetaData.class, metaDataId);

        if (toUpdate != null) {
            toUpdate.setCctvId(updatedMetadata.getCctvId());
            toUpdate.setAbnormalType(updatedMetadata.getAbnormalType());
            toUpdate.setType(updatedMetadata.getType());
            toUpdate.setQuality(updatedMetadata.getQuality());
            toUpdate.setVideoId(updatedMetadata.getVideoId());
            toUpdate.setFoundTime(updatedMetadata.getFoundTime());
            toUpdate.setPhotoId(updatedMetadata.getPhotoId());

            em.merge(toUpdate);
        }
    }
}
