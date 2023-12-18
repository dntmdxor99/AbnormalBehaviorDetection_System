package com.abnormal.detection;


import com.abnormal.detection.repository.user.JpaUserRepository;
import com.abnormal.detection.repository.user.UserRepository;
import com.abnormal.detection.service.user.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final EntityManager entityManager;
    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Bean
    public UserService userService() {return new UserService(userRepository());
    }
    @Bean
    public UserRepository userRepository() {return new JpaUserRepository(entityManager);}

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
