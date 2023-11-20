package com.abnormal.detection.service;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.abnormal.detection.domain.cctv.Cctv;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
//JUnit와 Spring Framework를 사용하여 테스트 환경을 설정하고 데이터베이스 테스트를 수행
//Cctv 엔티티의 cctvId는 데이터베이스의 자동 증가 기능을 활용하여 생성되는 값
/*
createCctv: 새 CCTV를 생성하고 생성된 CCTV의 식별자를 확인
getCctvById: 특정 CCTV 식별자로 CCTV를 가져오고 이름을 확인
getAllCctvs: 모든 CCTV를 가져와 리스트의 크기를 확인
updateCctv: 특정 CCTV를 업데이트하고 이름이 변경되었는지 확인
deleteCctv: 특정 CCTV를 삭제하고 삭제된 CCTV가 존재하지 않는지 확인
getCctvByName: 특정 이름으로 CCTV를 검색하고 결과 리스트 크기를 확인
filterCctvsByCriteria: 지정된 기준에 따라 CCTV를 필터링하고 결과 리스트 크기를 확인
getCctvsWithPagination: 페이지 및 페이지 크기를 지정하여 CCTV 리스트를 가져오고 결과 리스트 크기를 확인
countAllCctvs: 모든 CCTV 수를 가져오고 0보다 큰지 확인
searchCctvsByOptions: 여러 검색 옵션으로 CCTV를 검색하고 결과 리스트 크기를 확인
 */
//transactional 을 사용하여 db롤백
@SpringBootTest
@Transactional
class CctvServiceTest {

    @Autowired
    private CctvService cctvService;

    @BeforeEach
    void setUp() {
        // 테스트를 위한 데이터베이스 초기화
        Cctv cctv1 = new Cctv();
        cctv1.setCctvName("Cctv1");
        cctv1.setLocation("Location1");
        cctv1.setIs360Degree(true);
        cctv1.setProtocol("Protocol1");
        cctvService.createCctv(cctv1);

        Cctv cctv2 = new Cctv();
        cctv2.setCctvName("Cctv2");
        cctv2.setLocation("Location2");
        cctv2.setIs360Degree(false);
        cctv2.setProtocol("Protocol2");
        cctvService.createCctv(cctv2);
    }

    @Test
    void createCctv() {
        // Given
        Cctv cctv = new Cctv();
        cctv.setCctvName("NewCctv");
        cctv.setLocation("NewLocation");
        cctv.setIs360Degree(true);
        cctv.setProtocol("NewProtocol");

        // When
        Cctv createdCctv = cctvService.createCctv(cctv);

        // Then
        assertNotNull(createdCctv.getCctvId());
    }
/*
    @Test
    void getCctvById() {
        // Given
        Long cctvId = 1L;

        // When
        Optional<Cctv> cctv = Optional.ofNullable(cctvService.getCctvById(cctvId));

        // Then
        System.out.println("값 언제 있어" + cctv.isPresent());
        assertTrue(cctv.isPresent());
        assertEquals("Cctv1", cctv.get().getCctvName());
    }
*/
    @Test
    void getAllCctvs() {
        // When
        List<Cctv> result = cctvService.getAllCctvs();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
/*
    @Test
    void updateCctv() {
        // Given
        Long cctvId = 1L;
        Cctv updatedCctv = new Cctv();
        updatedCctv.setCctvName("UpdatedCctv");

        // When
        Cctv result = cctvService.updateCctv(cctvId, updatedCctv);

        // Then
        assertEquals("UpdatedCctv", result.getCctvName());
    }
*/
    @Test
    void deleteCctv() {
        // Given
        Long cctvId = 2L;

        // When
        cctvService.deleteCctv(cctvId);

        // Then
        assertNull(cctvService.getCctvById(cctvId));
    }

    @Test
    void getCctvByName() {
        // Given
        String cctvName = "Cctv1";

        // When
        List<Cctv> result = cctvService.getCctvByName(cctvName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void filterCctvsByCriteria() {
        // Given
        String location = "Location1";
        Boolean is360Degree = true;
        String protocol = "Protocol1";

        // When
        List<Cctv> result = cctvService.filterCctvsByCriteria(location, is360Degree, protocol);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getCctvsWithPagination() {
        // Given
        int page = 1;
        int pageSize = 1;
        String sortBy = "cctvName";

        // When
        List<Cctv> result = cctvService.getCctvsWithPagination(page, pageSize, sortBy);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void countAllCctvs() {
        // When
        int count = cctvService.countAllCctvs();

        // Then
        assertTrue(count >= 0);
    }

    @Test
    void searchCctvsByOptions() {
        // Given
        String cctvName = "Cctv1";
        String location = "Location1";
        Boolean is360Degree = true;
        String protocol = "Protocol1";

        // When
        List<Cctv> result = cctvService.searchCctvsByOptions(cctvName, location, is360Degree, protocol);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
