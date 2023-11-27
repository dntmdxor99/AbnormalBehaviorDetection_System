package com.abnormal.detection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class DetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectionApplication.class, args);
	}
}
