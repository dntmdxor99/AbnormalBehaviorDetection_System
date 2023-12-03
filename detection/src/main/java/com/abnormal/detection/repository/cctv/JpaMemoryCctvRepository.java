package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Slf4j
@RequiredArgsConstructor
public class JpaMemoryCctvRepository implements CctvRepository {

    @PersistenceContext
    private EntityManager entityManager;
/*

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cctvId;

    private String cctvName;
    private String location;
    private Float latitude;// 위도
    private Float longitude;// 경도
    private Boolean is360Degree;//true,f로 초기화
    private String channel;
    private String videoSize;
 */
    private final JpaCctvRepositoryLegend jpaCctvRepositoryLegend;
    public Cctv makeCctv(String cctvName, String location, Float latitude, Float longitude, Boolean is360Degree,String channel, String videoSize){
        Cctv cctv = new Cctv();
        cctv.setCctvName(cctvName);
        cctv.setLocation(location);//주소값
        cctv.setLatitude(latitude);//위도
        cctv.setLongitude(longitude);//경도
        cctv.setIs360Degree(is360Degree);
        cctv.setChannel(channel);//cctvip
        cctv.setVideoSize(videoSize);

        return cctv;
    }

    @PostConstruct
    public void init() {
        try {
            List<Cctv> cctvs = new ArrayList<>();
            cctvs.add(makeCctv("우경정보길", "대구광역시 북구 대현로15길 17", (float)35.8845360, (float)128.608639, Boolean.TRUE, "35.884216f", "1920x1080"));
            cctvs.add(makeCctv("끝돈", "대구광역시 북구 대학로23길 5", (float)35.8947285, (float)128.611282,Boolean.TRUE, "127.33.44.5", "1920x1080"));
            cctvs.add(makeCctv("대규집", "대구광역시 북구 경대로7길 76", (float)35.886183654708, (float)128.606278956326,Boolean.TRUE, "127.33.44.5", "1920x1080"));
            cctvs.add(makeCctv("명빈 반지하집", "대구광역시 북구 대학로 80", (float)35.8890974884948, (float)128.614322336303,Boolean.TRUE, "127.33.44.5", "1920x1080"));
            cctvs.add(makeCctv("한솔집", "대구광역시 북구 대현로19길 76-5", (float)35.8854896881863, (float)128.613390671173,Boolean.TRUE, "127.33.44.5", "1920x1080"));
            cctvs.add(makeCctv("세아집", "대구광역시 북구 경대로7길 76-1", (float)35.8862368639416, (float)128.606147811371,Boolean.TRUE, "127.33.44.5", "1920x1080"));
            for (Cctv cctv : cctvs) {
                jpaCctvRepositoryLegend.createCctv(cctv);
                log.info("Cctv inserted: {}", cctv.getCctvName());
            }
        } catch (Exception e) {
            log.error("Error during initialization: " + e.getMessage());
        }
    }
    @Override
    public Cctv createCctv(Cctv cctv) {
        entityManager.persist(cctv);
        return cctv;
    }

    //위도경도
    /*
    @Override
    public List<Cctv> getCctvsByLocation(Float latitude, Float longitude) {
        TypedQuery<Cctv> query = entityManager.createQuery(
                "SELECT c FROM Cctv c WHERE c.latitude = :latitude AND c.longitude = :longitude",
                Cctv.class
        );
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        return query.getResultList();
    }

    @Override
    public List<Cctv> getCctvsNearLocation(Float latitude, Float longitude, double distance) {
        TypedQuery<Cctv> query = entityManager.createQuery(
                "SELECT c FROM Cctv c WHERE FUNCTION('distance', c.latitude, c.longitude, :latitude, :longitude) <= :distance",
                Cctv.class
        );
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setParameter("distance", distance);
        return query.getResultList();
    }

    @Override
    public List<Cctv> getCctvsByLocationAndDistance(Float latitude, Float longitude, double distance) {
        TypedQuery<Cctv> query = entityManager.createQuery(
                "SELECT c FROM Cctv c WHERE c.latitude = :latitude AND c.longitude = :longitude AND FUNCTION('distance', c.latitude, c.longitude, :latitude, :longitude) <= :distance",
                Cctv.class
        );
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setParameter("distance", distance);
        return query.getResultList();
    }

     */
    //위도경도

    @Override
    public Cctv getCctvById(Long cctvId) {
        return entityManager.find(Cctv.class, cctvId);
    }

    @Override
    public List<Cctv> getAllCctvs() {
        return entityManager.createQuery("SELECT c FROM Cctv c", Cctv.class).getResultList();
    }

    @Override
    public Cctv updateCctv(Long cctvId, Cctv updatedCctv) {
        Cctv existingCctv = getCctvById(cctvId);
        if (existingCctv != null) {
            existingCctv.setCctvName(updatedCctv.getCctvName());
            existingCctv.setLocation(updatedCctv.getLocation());
            existingCctv.setLatitude(updatedCctv.getLatitude());
            existingCctv.setLongitude(updatedCctv.getLongitude());
            existingCctv.setIs360Degree(updatedCctv.getIs360Degree());
            existingCctv.setChannel(updatedCctv.getChannel());
            existingCctv.setVideoSize(updatedCctv.getVideoSize());
        }
        return existingCctv;
    }

    @Override
    public void deleteCctv(Long cctvId) {
        Cctv cctv = getCctvById(cctvId);
        if (cctv != null) {
            entityManager.remove(cctv);
        }
    }

    @Override
    public List<Cctv> getCctvByName(String cctvName) {
        return entityManager.createQuery("SELECT c FROM Cctv c WHERE c.cctvName = :cctvName", Cctv.class)
                .setParameter("cctvName", cctvName)
                .getResultList();
    }

    @Override
    public List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String channel) {
        String query = "SELECT c FROM Cctv c WHERE (:location IS NULL OR c.location = :location) " +
                "AND (:is360Degree IS NULL OR c.is360Degree = :is360Degree) " +
                "AND (:channel IS NULL OR c.channel = :channel)";
        return entityManager.createQuery(query, Cctv.class)
                .setParameter("location", location)
                .setParameter("is360Degree", is360Degree)
                .setParameter("channel", channel)
                .getResultList();
    }

    @Override
    public List<Cctv> getCctvsWithPagination(int page, int pageSize, String sortBy) {
        String query = "SELECT c FROM Cctv c";
        if (sortBy != null) {
            query += " ORDER BY c." + sortBy;
        }
        return entityManager.createQuery(query, Cctv.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int countAllCctvs() {
        Long count = (Long) entityManager.createQuery("SELECT COUNT(c) FROM Cctv c").getSingleResult();
        return count.intValue();
    }
/*
    @Override
    public List<Cctv> searchCctvsByOptions(String cctvName, String location, Boolean is360Degree, String protocol) {
        String query = "SELECT c FROM Cctv c " +
                "WHERE (:cctvName IS NULL OR c.cctvName = :cctvName) " +
                "AND (:location IS NULL OR c.location = :location) " +
                "AND (:is360Degree IS NULL OR c.is360Degree = :is360Degree) " +
                "AND (:protocol IS NULL OR c.protocol = :protocol)";
        return entityManager.createQuery(query, Cctv.class)
                .setParameter("cctvName", cctvName)
                .setParameter("location", location)
                .setParameter("is360Degree", is360Degree)
                .setParameter("protocol", protocol)
                .getResultList();
    }

 */
    @Override
    public List<Cctv> searchCctvsByOptions(Long cctvId, String cctvName, String location, Boolean is360Degree, String channel) {
        String query = "SELECT c FROM Cctv c " +
                "WHERE (:cctvId IS NULL OR c.cctvId = :cctvId) " +
                "AND (COALESCE(:cctvName, '') = '' OR LOWER(c.cctvName) LIKE LOWER(CONCAT('%', :cctvName, '%'))) " +
                "AND (COALESCE(:location, '') = '' OR LOWER(c.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
                "AND (:is360Degree IS NULL OR c.is360Degree = :is360Degree) " +
                "AND (COALESCE(:channel, '') = '' OR LOWER(c.channel) LIKE LOWER(CONCAT('%', :channel, '%')))";
        return entityManager.createQuery(query, Cctv.class)
                .setParameter("cctvId", cctvId)
                .setParameter("cctvName", cctvName)
                .setParameter("location", location)
                .setParameter("is360Degree", is360Degree)
                .setParameter("channel", channel)
                .getResultList();
    }



}

