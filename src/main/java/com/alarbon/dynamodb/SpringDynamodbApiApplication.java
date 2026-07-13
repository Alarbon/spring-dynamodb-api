package com.alarbon.dynamodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.alarbon.dynamodb")
public class SpringDynamodbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDynamodbApiApplication.class, args);
    }
}