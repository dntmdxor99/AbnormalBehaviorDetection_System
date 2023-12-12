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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.abnormal.detection.domain.metadata.AbnormalType.*;
import static com.abnormal.detection.domain.metadata.EntityType.PERSON;
import static com.abnormal.detection.domain.metadata.Quality.*;


@Repository
@Slf4j
@RequiredArgsConstructor
public class JpaMetaDataRepository implements MetaDataRepository{

    @PersistenceContext
    private final EntityManager em;

//디비 더미 insert
    private final JpaMetaDataRepositoryLegend jpaMetaDataRepositoryLegend;

    public MetaData makeMetaData(String foundTime, String entityFoundTime, Long cctvId, EntityType type, AbnormalType abnormalType, Quality quality, Long videoId, Long photoId, String base64Image) {
        MetaData metaData = new MetaData();
        metaData.setFoundTime(parseDate(foundTime));
        metaData.setEntityFoundTime(parseDate(entityFoundTime));
        metaData.setCctvId(cctvId);
        metaData.setType(type);
        metaData.setAbnormalType(abnormalType);
        metaData.setQuality(quality);
        metaData.setVideoId(videoId);
        metaData.setPhotoId(photoId);
        metaData.setBase64Image(base64Image);

        return metaData;
    }

    @PostConstruct
    public void init() {
        try {
            List<MetaData> metaDatas = new ArrayList<>();
            metaDatas.add(makeMetaData("2020-08-06T12:04:00", "2020-08-06T12:05:00", 1L, PERSON, fight, HIGH, 1L, 1L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2021-08-07T12:04:00", "2020-08-06T12:05:00", 1L, PERSON, assault, MIDDLE, 1L, 2L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2022-08-08T12:04:00", "2020-08-06T12:05:00", 1L, PERSON, drunken, LOW, 1L, 3L,"asdfghjkl"));

            metaDatas.add(makeMetaData("2020-08-06T12:04:00", "2020-08-06T12:05:00", 2L, PERSON, swoon, HIGH, 2L, 4L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2021-08-07T12:04:00", "2020-08-06T12:05:00", 2L, PERSON, kidnap, MIDDLE, 2L, 5L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2022-08-08T12:04:00", "2020-08-06T12:05:00", 2L, PERSON, fight, LOW, 2L, 6L,"asdfghjkl"));

            metaDatas.add(makeMetaData("2020-08-06T12:04:00", "2020-08-06T12:05:00", 3L, PERSON, fight, HIGH, 3L, 7L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2021-08-07T12:04:00", "2020-08-06T12:05:00", 3L, PERSON, fight, MIDDLE, 3L, 8L,"asdfghjkl"));
            metaDatas.add(makeMetaData("2022-08-08T12:04:00", "2020-08-06T12:05:00", 3L, PERSON, fight, LOW, 3L, 9L,"asdfghjkl"));

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
    ////f실제로 객체가 언제로 이상행동검출
    ////en 해당 비디오 내에서 몇번째 프레임인지
    /*
    @Override
    public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
        return em.createQuery("SELECT m FROM MetaData m WHERE m.foundTime >= :startDate and m.foundTime <= :endDate", MetaData.class)
                .setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
    }

     */

/*
    @Override
    public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
        // 시작일은 2020-08-06T03:03:00의 2020-08-06T00:00:00으로 설정
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        // 종료일은 2020-08-06T04:07:00의 2020-08-07T00:00:00으로 설정
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);

        return em.createQuery("SELECT m FROM MetaData m WHERE m.foundTime >= :startDate AND m.foundTime <= :endDate", MetaData.class)
                .setParameter("startDate", startCalendar.getTime())
                .setParameter("endDate", endCalendar.getTime())
                .getResultList();
    }

 */

    //요약: 세부검색기능으로 프론트에서 Date형식인 startDate,endDate를 사용자에게 받으면 그에 날짜 해당되는 모든 메타데이터를 반환해줌 여기서 foundtime,entityFoundTime세트로 화면에 띄우고 리스트 클릭시에 메타데이터 리스트로 담아서 search 요청
    //1.화면상에 사용자에게 date 날짜 입력받음 ->백에게 /metadata/date-range 요청 후 foundtime,entityFoundTime세트 받음
    //2.화면상에 foundtime,entityFoundTime보여주고 사용자가 클릭시에
    // /metadata/search 요청("foundTime": "", <-사용자 클릭시 여기포함
    //        "entityFoundTime": "", <- 사용자 클릭시 여기포함
    //        "cctvId": ,
    //        "abnormalType": "")에 포함시켜서 옵션 검색
    //참고로 옵션검색은 한개라도 일치하면 검색됨
public List<MetaData> getMetadataByDateRange(Date startDate, Date endDate) {
    // 시작일은 2020-08-06T03:03:00의 2020-08-06T00:00:00으로 설정
    Calendar startCalendar = Calendar.getInstance();
    startCalendar.setTime(startDate);
    startCalendar.set(Calendar.HOUR_OF_DAY, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MILLISECOND, 0);

    // 종료일은 2020-08-06T04:07:00의 2020-08-07T00:00:00으로 설정
    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTime(endDate);
    endCalendar.set(Calendar.HOUR_OF_DAY, 23);
    endCalendar.set(Calendar.MINUTE, 59);
    endCalendar.set(Calendar.SECOND, 59);
    endCalendar.set(Calendar.MILLISECOND, 999);

    return em.createQuery("SELECT m FROM MetaData m WHERE m.foundTime BETWEEN :startDate AND :endDate", MetaData.class)
            .setParameter("startDate", startCalendar.getTime())
            .setParameter("endDate", endCalendar.getTime())
            .getResultList();
}



    //한개라도 일치하는 값 모두 반환
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

    //version 3 searchLegendByOptions
    @Override
    public List<MetaData> searchLegendByOptions(Date startDate, Date endDate, Long cctvId, AbnormalType abnormalType) {
        // 시작일과 종료일을 생성할 때 null 체크
        Calendar startCalendar1 = startDate != null ? Calendar.getInstance() : null;
        if (startCalendar1 != null) {
            startCalendar1.setTime(startDate);
            startCalendar1.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar1.set(Calendar.MINUTE, 0);
            startCalendar1.set(Calendar.SECOND, 0);
            startCalendar1.set(Calendar.MILLISECOND, 0);
        }

        Calendar endCalendar2 = endDate != null ? Calendar.getInstance() : null;
        if (endCalendar2 != null) {
            endCalendar2.setTime(endDate);
            endCalendar2.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar2.set(Calendar.MINUTE, 59);
            endCalendar2.set(Calendar.SECOND, 59);
            endCalendar2.set(Calendar.MILLISECOND, 999);
        }
/*
//원본
        String query = "SELECT m FROM MetaData m " +
                "WHERE (:cctvId IS NULL OR m.cctvId = :cctvId) " +
                "AND (:abnormalType IS NULL OR m.abnormalType = :abnormalType) " +
                // 추가: 시작일과 종료일이 모두 null이 아닌 경우에만 해당 조건을 추가
                "AND (:startDate IS NULL AND :endDate IS NULL OR m.foundTime BETWEEN :startDate AND :endDate)";

//version 1
        String query = "SELECT DISTINCT m FROM MetaData m " +
                "WHERE (:cctvId IS NULL OR m.cctvId = :cctvId) " +
                "AND (:abnormalType IS NULL OR m.abnormalType = :abnormalType) " +
                // 추가: 시작일과 종료일이 모두 null이 아닌 경우에만 해당 조건을 추가
                "AND (:startDate IS NULL AND :endDate IS NULL OR m.foundTime BETWEEN :startDate AND :endDate)";


 */
        String query = "SELECT m FROM MetaData m " +
                "WHERE (:cctvId IS NULL OR m.cctvId = :cctvId) " +
                "AND (:abnormalType IS NULL OR m.abnormalType = :abnormalType) " +
                "AND (:startDate IS NULL AND :endDate IS NULL OR m.foundTime BETWEEN :startDate AND :endDate) " +
                "GROUP BY m.metaDataId"; // 고유 식별자로 그룹화

        return em.createQuery(query, MetaData.class)
                .setParameter("cctvId", cctvId)
                .setParameter("abnormalType", abnormalType)
                .setParameter("startDate", startCalendar1 != null ? startCalendar1.getTime() : null)
                .setParameter("endDate", endCalendar2 != null ? endCalendar2.getTime() : null)
                .getResultList();
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
