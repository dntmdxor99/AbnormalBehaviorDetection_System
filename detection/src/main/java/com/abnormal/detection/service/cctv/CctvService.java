package com.abnormal.detection.service.cctv;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.repository.cctv.CctvRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CctvService {

    private final CctvRepository cctvRepository;

    @Autowired
    public CctvService(CctvRepository cctvRepository) {
        this.cctvRepository = cctvRepository;
    }

    @Transactional
    public Cctv createCctv(Cctv cctv) {
        return cctvRepository.createCctv(cctv);
    }

    public Cctv getCctvById(Long cctvId) {
        return cctvRepository.getCctvById(cctvId);
    }

    public List<Cctv> getAllCctvs() {
        return cctvRepository.getAllCctvs();
    }

    // 위도경도
    /*
    public List<Cctv> getCctvsByLocation(Float latitude, Float longitude) {
        return cctvRepository.getCctvsByLocation(latitude, longitude);
    }

    public List<Cctv> getCctvsNearLocation(Float latitude, Float longitude, double distance) {
        return cctvRepository.getCctvsNearLocation(latitude, longitude, distance);
    }

    public List<Cctv> getCctvsByLocationAndDistance(Float latitude, Float longitude, double distance) {
        return cctvRepository.getCctvsByLocationAndDistance(latitude, longitude, distance);
    }

     */
    //

    @Transactional
    public Cctv updateCctv(Long cctvId, Cctv updatedCctv) {
        return cctvRepository.updateCctv(cctvId, updatedCctv);
    }

    @Transactional
    public void deleteCctv(Long cctvId) {
        cctvRepository.deleteCctv(cctvId);
    }

    public List<Cctv> getCctvByName(String cctvName) {
        return cctvRepository.getCctvByName(cctvName);
    }

    public List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String channel) {
        return cctvRepository.filterCctvsByCriteria(location, is360Degree, channel);
    }

    public List<Cctv> getCctvsWithPagination(int page, int pageSize, String sortBy) {
        return cctvRepository.getCctvsWithPagination(page, pageSize, sortBy);
    }

    public int countAllCctvs() {
        return cctvRepository.countAllCctvs();
    }

    public List<Cctv> searchCctvsByOptions(String cctvName, String location, Boolean is360Degree, String channel) {
        return cctvRepository.searchCctvsByOptions(cctvName, location, is360Degree, channel);
    }
}

