package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import com.abnormal.detection.repository.cctv.JpaCctvRepositoryLegend;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.abnormal.detection.domain.metadata.AbnormalType.fight;
import static com.abnormal.detection.domain.metadata.EntityType.PERSON;
import static com.abnormal.detection.domain.metadata.Quality.HIGH;


@Repository
@Slf4j
@RequiredArgsConstructor
public class JpaMetaDataRepository implements MetaDataRepository{

    @PersistenceContext
    private final EntityManager em;


//디비 더미 insert
    private final JpaMetaDataRepositoryLegend jpaMetaDataRepositoryLegend;

    public MetaData makeMetaData(String foundTime, String entityFoundTime, Long cctvId, EntityType type, AbnormalType abnormalType, Quality quality, Long videoId, Long photoId) {
        MetaData metaData = new MetaData();
        metaData.setFoundTime(parseDate(foundTime));
        metaData.setEntityFoundTime(parseDate(entityFoundTime));
        metaData.setCctvId(cctvId);
        metaData.setType(type);
        metaData.setAbnormalType(abnormalType);
        metaData.setQuality(quality);
        metaData.setVideoId(videoId);
        metaData.setPhotoId(photoId);

        return metaData;
    }

    @PostConstruct
    public void init() {
        try {
            List<MetaData> metaDatas = new ArrayList<>();
            metaDatas.add(makeMetaData("2020-08-06T12:04:00", "2020-08-06T12:05:00", 1L, PERSON, fight, HIGH, 1L, 1L));

            for (MetaData metaData : metaDatas) {
                jpaMetaDataRepositoryLegend.createMetaData(metaData);
                log.info("MetaData inserted: {}", metaData.getAbnormalType());
            }
        } catch (Exception e) {
            log.error("Error during initialization", e);
        }
    }

    private Date parseDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

            return Timestamp.valueOf(localDateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
//


    @Override
    public MetaData createMetaData(MetaData metaData) {
        em.persist(metaData);
        return metaData;
    }

    @Override
    public List<MetaData> getAllMetaDatas() {
        return em.createQuery("SELECT m FROM MetaData m", MetaData.class).getResultList();
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
    @Override
    public List<MetaData> searchMetaDatasByOptions(Date foundTime, Date entityFoundTime, Long cctvId, AbnormalType abnormalType) {
        String query = "SELECT m FROM MetaData m " +
                "WHERE (:cctvId IS NULL OR m.cctvId = :cctvId) " +
                "AND (:abnormalType IS NULL OR m.abnormalType = :abnormalType) " +
                "AND (COALESCE(:foundTime, '') = '' OR m.foundTime = :foundTime) " +
                "AND (COALESCE(:entityFoundTime, '') = '' OR m.entityFoundTime = :entityFoundTime)";

        return em.createQuery(query, MetaData.class)
                .setParameter("cctvId", cctvId)
                .setParameter("abnormalType", abnormalType)
                .setParameter("foundTime", foundTime)
                .setParameter("entityFoundTime", entityFoundTime)
                .getResultList();
    }
}
