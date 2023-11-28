package com.example.tttn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TttnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TttnApplication.class, args);
    }

}
