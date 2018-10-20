package com.tnbank.microtransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class MicrotransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrotransactionApplication.class, args);
	}
}
