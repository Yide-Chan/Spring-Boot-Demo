package com.chenyide.adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerDemoApplication.class, args);
    }

}
