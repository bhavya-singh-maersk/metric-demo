package com.maersk.eacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class EacloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EacloudApplication.class, args);
	}

}
