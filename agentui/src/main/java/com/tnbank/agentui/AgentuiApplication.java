package com.tnbank.agentui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgentuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentuiApplication.class, args);
	}
}
