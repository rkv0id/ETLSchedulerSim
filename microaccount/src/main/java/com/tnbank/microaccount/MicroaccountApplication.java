package com.tnbank.microaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroaccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroaccountApplication.class, args);
	}
}
