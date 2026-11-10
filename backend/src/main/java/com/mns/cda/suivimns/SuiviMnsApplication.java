package com.mns.cda.suivimns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class SuiviMnsApplication {

    public static void main(String[] args) {
        // All LocalDateTime values (now(), JPA auditing, stored timestamps) are UTC, on every machine:
        // a dev box in Europe/Paris and the server must agree. Display conversion is the browser's job.
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

        SpringApplication.run(SuiviMnsApplication.class, args);
    }

}
