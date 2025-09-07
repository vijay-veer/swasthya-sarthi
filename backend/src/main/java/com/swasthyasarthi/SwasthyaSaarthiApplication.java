package com.swasthyasarthi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SwasthyaSaarthiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwasthyaSaarthiApplication.class, args);
    }
}