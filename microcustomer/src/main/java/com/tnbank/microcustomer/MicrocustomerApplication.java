package com.tnbank.microcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicrocustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocustomerApplication.class, args);
	}
}
