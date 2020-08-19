package com.dreamsecurity.shopface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShopfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopfaceApplication.class, args);
    }
}
