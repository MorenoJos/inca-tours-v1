package com.incatours.incatours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IncaToursApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncaToursApplication.class, args);
    }

}
