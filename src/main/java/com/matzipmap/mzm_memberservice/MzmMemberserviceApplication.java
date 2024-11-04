package com.matzipmap.mzm_memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MzmMemberserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzmMemberserviceApplication.class, args);
    }

}
