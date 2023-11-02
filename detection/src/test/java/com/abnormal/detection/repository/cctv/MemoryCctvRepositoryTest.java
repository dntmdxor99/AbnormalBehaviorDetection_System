/*

package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryCctvRepositoryTest {
    private MemoryCctvRepository cctvRepository;

    @BeforeEach
    void setUp() {
        cctvRepository = new MemoryCctvRepository();
    }

    @Test
    void createCctv() {
        // 새 CCTV 객체 생성
        Cctv newCctv = new Cctv(null, "CCTV 1", "Location 1", true, "Protocol 1", "HD");

        // CCTV 생성 및 반환된 객체 검증
        Cctv createdCctv = cctvRepository.createCctv(newCctv);
        assertNotNull(createdCctv.getCctvId());

        // 생성된 CCTV를 고유 ID로 조회하여 확인
        Cctv retrievedCctv = cctvRepository.getCctvById(createdCctv.getCctvId());
        assertNotNull(retrievedCctv);
        assertEquals(newCctv.getCctvName(), retrievedCctv.getCctvName());
    }

    @Test
    void getCctvById() {
        // 기존 CCTV를 찾아서 검증
        Cctv existingCctv = cctvRepository.getCctvById(1L);
        assertNotNull(existingCctv);
    }

    @Test
    void getAllCctvs() {
        // 모든 CCTV를 조회
        List<Cctv> cctvList = cctvRepository.getAllCctvs();
        assertNotNull(cctvList);
        assertTrue(cctvList.size() > 0);
    }

    @Test
    void updateCctv() {
        // 업데이트할 CCTV 생성
        Cctv updatedCctv = new Cctv(null, "Updated CCTV", "Updated Location", false, "Updated Protocol", "Full HD");

        // 기존 CCTV 업데이트
        Cctv existingCctv = cctvRepository.getCctvById(1L);
        assertNotNull(existingCctv);
        Cctv updated = cctvRepository.updateCctv(existingCctv.getCctvId(), updatedCctv);
        assertNotNull(updated);

        // 업데이트된 정보 확인
        assertEquals(updatedCctv.getCctvName(), updated.getCctvName());
        assertEquals(updatedCctv.getLocation(), updated.getLocation());
        assertEquals(updatedCctv.getIs360Degree(), updated.getIs360Degree());
        assertEquals(updatedCctv.getProtocol(), updated.getProtocol());
        assertEquals(updatedCctv.getVideoSize(), updated.getVideoSize());
    }

    @Test
    void deleteCctv() {
        // 삭제할 CCTV ID 지정
        Long cctvIdToDelete = 1L;

        // CCTV 삭제
        cctvRepository.deleteCctv(cctvIdToDelete);

        // 삭제 후 해당 ID로 CCTV 조회
        Cctv deletedCctv = cctvRepository.getCctvById(cctvIdToDelete);
        assertNull(deletedCctv);
    }

    @Test
    void getCctvByName() {
        // CCTV 이름으로 CCTV 목록 검색
        List<Cctv> cctvs = cctvRepository.getCctvByName("CCTV 1");
        assertNotNull(cctvs);
        assertTrue(cctvs.size() > 0);
    }

    @Test
    void filterCctvsByCriteria() {
        // 조건에 따라 CCTV 필터링
        List<Cctv> filteredCctvs = cctvRepository.filterCctvsByCriteria("Location 1", true, "Protocol 1");
        assertNotNull(filteredCctvs);
        assertTrue(filteredCctvs.size() > 0);
    }

    @Test
    void getCctvsWithPagination() {
        // 페이징 및 정렬된 CCTV 목록 조회
        List<Cctv> cctvs = cctvRepository.getCctvsWithPagination(1, 3, "cctvName");
        assertNotNull(cctvs);
        assertTrue(cctvs.size() <= 3);
    }

    @Test
    void countAllCctvs() {
        // 모든 CCTV의 총 개수 조회
        int count = cctvRepository.countAllCctvs();
        assertTrue(count > 0);
    }

    @Test
    void searchCctvsByOptions() {
        // 다양한 검색 옵션을 사용하여 CCTV 검색
        List<Cctv> cctvs = cctvRepository.searchCctvsByOptions("CCTV 1", "Location 1", true, "Protocol 1");
        assertNotNull(cctvs);
        assertTrue(cctvs.size() > 0);
    }
}
*/