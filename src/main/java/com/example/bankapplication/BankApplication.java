package com.example.bankapplication;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
    @Bean
    public CommandLineRunner printDbUrl(@Value("${spring.datasource.url}") String dbUrl) {
        return args -> {
            System.out.println("==========================================");
            System.out.println("APP CONNECTED TO DATABASE URL: " + dbUrl);
            System.out.println("==========================================");
        };
    }
}
