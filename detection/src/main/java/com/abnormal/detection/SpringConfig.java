package com.abnormal.detection;

import com.abnormal.detection.repository.cctv.CctvRepository;
import com.abnormal.detection.repository.cctv.JpaMemoryCctvRepository;
import com.abnormal.detection.service.CctvService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }
/*
    @Bean
    public CctvRepository cctvRepository() {
        return new JpaMemoryCctvRepository(em);
    }

    @Bean
    public CctvService cctvService() {
        return new CctvService(cctvRepository());
    }

 */
}
