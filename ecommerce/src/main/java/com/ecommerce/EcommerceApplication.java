package com.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.services.S3Service;

@SpringBootApplication
public class EcommerceApplication {



	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
