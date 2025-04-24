package com.waitless.benefit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.waitless.common","com.waitless.benefit"})
public class BenefitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenefitApplication.class, args);
	}

}
