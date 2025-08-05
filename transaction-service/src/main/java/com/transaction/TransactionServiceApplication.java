package com.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableFeignClients(basePackages = "com.transaction.client")
public class TransactionServiceApplication {

	public static void main(String[] args) {
		System.out.println("hi");
		SpringApplication.run(TransactionServiceApplication.class, args);
	}
	
}
