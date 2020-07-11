package com.example.PriceListService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PriceListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceListServiceApplication.class, args);
	}

}
