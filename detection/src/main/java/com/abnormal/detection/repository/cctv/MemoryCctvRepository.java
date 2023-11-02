package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryCctvRepository implements CctvRepository {
    private List<Cctv> cctvList;

    public MemoryCctvRepository() {
        cctvList = new ArrayList<>();
    }

    // CCTV 생성 및 저장
    // 새로운 고유 ID 생성
    // 반환: 생성된 CCTV 객체
    @Override
    public Cctv createCctv(Cctv cctv) {
        // 고유 ID 생성 및 설정
        Long nextId = (long) (cctvList.size() + 1);
        cctv.setCctvId(nextId);
        cctvList.add(cctv);
        return cctv;
    }

    // 고유 ID로 CCTV 조회
    // 반환: 해당 고유 ID에 해당하는 CCTV 객체
    @Override
    public Cctv getCctvById(Long cctvId) {
        return cctvList.stream()
                .filter(c -> c.getCctvId().equals(cctvId))
                .findFirst()
                .orElse(null);
    }

    // 모든 CCTV 목록 조회
    // 반환: 모든 CCTV 목록
    @Override
    public List<Cctv> getAllCctvs() {
        return cctvList;
    }

    // 고유 ID로 CCTV 업데이트
    // 반환: 업데이트된 CCTV 객체 또는 null (업데이트 실패)
    @Override
    public Cctv updateCctv(Long cctvId, Cctv updatedCctv) {
        Cctv existingCctv = getCctvById(cctvId);
        if (existingCctv != null) {
            existingCctv.setCctvName(updatedCctv.getCctvName());
            existingCctv.setLocation(updatedCctv.getLocation());
            existingCctv.setIs360Degree(updatedCctv.getIs360Degree());
            existingCctv.setProtocol(updatedCctv.getProtocol());
            existingCctv.setVideoSize(updatedCctv.getVideoSize());
            return existingCctv;
        }
        return null;
    }

    // 고유 ID로 CCTV 삭제
    @Override
    public void deleteCctv(Long cctvId) {
        cctvList = cctvList.stream()
                .filter(c -> !c.getCctvId().equals(cctvId))
                .collect(Collectors.toList());
    }

    // CCTV 이름을 사용하여 CCTV 목록 검색
    // 반환: 해당 이름과 일치하는 CCTV 목록
    @Override
    public List<Cctv> getCctvByName(String cctvName) {
        return cctvList.stream()
                .filter(c -> c.getCctvName().equals(cctvName))
                .collect(Collectors.toList());
    }

    // 조건에 따라 CCTV 필터링
    // 반환: 조건에 부합하는 CCTV 목록
    @Override
    public List<Cctv> filterCctvsByCriteria(String location, Boolean is360Degree, String protocol) {
        return cctvList.stream()
                .filter(c -> (location == null || c.getLocation().equals(location))
                        && (is360Degree == null || c.getIs360Degree().equals(is360Degree))
                        && (protocol == null || c.getProtocol().equals(protocol)))
                .collect(Collectors.toList());
    }

    // 페이징 및 정렬된 CCTV 목록 조회
    // 반환: 페이징 및 정렬이 적용된 CCTV 목록
    @Override
    public List<Cctv> getCctvsWithPagination(int page, int pageSize, String sortBy) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, cctvList.size());
        if (startIndex < cctvList.size() && startIndex < endIndex) {
            List<Cctv> sortedCctvs = cctvList.subList(startIndex, endIndex);
            if (sortBy != null) {
                sortedCctvs.sort((c1, c2) -> {
                    if ("cctvName".equals(sortBy)) {
                        return c1.getCctvName().compareTo(c2.getCctvName());
                    } else {
                        return 0; // No sorting
                    }
                });
            }
            return sortedCctvs;
        }
        return new ArrayList<>();
    }

    // 모든 CCTV의 총 개수 반환
    @Override
    public int countAllCctvs() {
        return cctvList.size();
    }

    // 다양한 검색 옵션을 사용하여 CCTV 검색
    // 반환: 다양한 검색 옵션에 부합하는 CCTV 목록
    @Override
    public List<Cctv> searchCctvsByOptions(String cctvName, String location, Boolean is360Degree, String protocol) {
        return cctvList.stream()
                .filter(c -> (cctvName == null || c.getCctvName().equals(cctvName))
                        && (location == null || c.getLocation().equals(location))
                        && (is360Degree == null || c.getIs360Degree().equals(is360Degree))
                        && (protocol == null || c.getProtocol().equals(protocol)))
                .collect(Collectors.toList());
    }
}
