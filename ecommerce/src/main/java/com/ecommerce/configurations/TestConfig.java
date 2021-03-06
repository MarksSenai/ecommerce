package com.ecommerce.configurations;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ecommerce.services.interfaces.EmailService;
import com.ecommerce.services.mock.MockEmailService;
import com.ecommerce.services.validation.DBService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

//    @Bean
//    public EmailService emailService() {
//        return new MockEmailService();
//    }
}