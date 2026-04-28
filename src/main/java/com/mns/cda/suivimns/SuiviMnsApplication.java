package com.mns.cda.suivimns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuiviMnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuiviMnsApplication.class, args);
    }

}
