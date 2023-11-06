package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class JpaMemoryCctvRepository implements CctvRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cctv createCctv(Cctv cctv) {
        entityManager.persist(cctv);
        return cctv;
    }

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
            existingCctv.setIs360Degree(updatedCctv.getIs360Degree());
            existingCctv.setProtocol(updatedCctv.getProtocol());
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
    public List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String protocol) {
        String query = "SELECT c FROM Cctv c WHERE (:location IS NULL OR c.location = :location) " +
                "AND (:is360Degree IS NULL OR c.is360Degree = :is360Degree) " +
                "AND (:protocol IS NULL OR c.protocol = :protocol)";
        return entityManager.createQuery(query, Cctv.class)
                .setParameter("location", location)
                .setParameter("is360Degree", is360Degree)
                .setParameter("protocol", protocol)
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
}

