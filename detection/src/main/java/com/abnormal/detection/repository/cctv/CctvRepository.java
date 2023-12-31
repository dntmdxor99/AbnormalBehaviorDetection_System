package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;

import java.util.List;

public interface CctvRepository{
    Cctv createCctv(Cctv cctv);
    List<Cctv> getAllCctvs();

    Cctv getCctvById(Long cctvId);

    /*

    List<Cctv> getCctvsByLocation(Float latitude, Float longitude);

    // 특정 위치 주변의 CCTV를 검색하는 메서드
    List<Cctv> getCctvsNearLocation(Float latitude, Float longitude, double distance);

    // 위도, 경도, 거리를 사용하여 CCTV를 검색하는 메서드
    List<Cctv> getCctvsByLocationAndDistance(Float latitude, Float longitude, double distance);

     */

    Cctv updateCctv(Long cctvId, Cctv updatedCctv);

    void deleteCctv(Long cctvId);

    // CCTV 이름을 사용하여 CCTV를 검색하는 메서드
    List<Cctv> getCctvByName(String cctvName);

    // 특정 조건에 따른 CCTV 필터링
    List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String channel);

    // 페이징 및 정렬
    List<Cctv> getCctvsWithPagination(int page, int pageSize, String sortBy);

    // CCTV 데이터에 대한 통계 정보
    int countAllCctvs();

    // 다양한 검색 옵션
    List<Cctv> searchCctvsByOptions(Long cctvId, String cctvName, String location, Boolean is360Degree, String channel);
}

