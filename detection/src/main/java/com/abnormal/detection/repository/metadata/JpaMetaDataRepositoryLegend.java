package com.abnormal.detection.repository.metadata;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.MetaData;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//테스트용 insert

@Service
@RequiredArgsConstructor
public class JpaMetaDataRepositoryLegend {
    private final EntityManager entityManager;

    @Transactional
    public MetaData createMetaData(MetaData metaData) {
        // 고유 ID 생성 및 설정
        //store.setStoreId(null); // ID는 자동 생성
        entityManager.persist(metaData);
        return metaData;
    }
}