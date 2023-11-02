package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;

import java.util.List;

public class JpaMemoryCctvRepository implements CctvRepository{
    @Override
    public Cctv createCctv(Cctv cctv) {
        return null;
    }

    @Override
    public Cctv getCctvById(Long cctvId) {
        return null;
    }

    @Override
    public List<Cctv> getAllCctvs() {
        return null;
    }

    @Override
    public Cctv updateCctv(Long cctvId, Cctv updatedCctv) {
        return null;
    }

    @Override
    public void deleteCctv(Long cctvId) {

    }

    @Override
    public List<Cctv> getCctvByName(String cctvName) {
        return null;
    }

    @Override
    public List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String protocol) {
        return null;
    }

    @Override
    public List<Cctv> getCctvsWithPagination(int page, int pageSize, String sortBy) {
        return null;
    }

    @Override
    public int countAllCctvs() {
        return 0;
    }

    @Override
    public List<Cctv> searchCctvsByOptions(String cctvName, String location, Boolean is360Degree, String protocol) {
        return null;
    }
}
