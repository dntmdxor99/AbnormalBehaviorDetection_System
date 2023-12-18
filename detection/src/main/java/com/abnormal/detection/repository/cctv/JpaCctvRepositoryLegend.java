package com.abnormal.detection.repository.cctv;

import com.abnormal.detection.domain.cctv.Cctv;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaCctvRepositoryLegend {
    private final EntityManager entityManager;

    @Transactional
    public Cctv createCctv(Cctv cctv) {
        // 고유 ID 생성 및 설정
        //store.setStoreId(null); // ID는 자동 생성
        entityManager.persist(cctv);
        return cctv;
    }
}
