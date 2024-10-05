package com.si642.billmanagementsi642projectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
@EnableJpaAuditing
public class BillManagementSi642ProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillManagementSi642ProjectBackendApplication.class, args);
    }



}
