package com.revert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.revert"})
@EnableScheduling
public class RevertApplication2 extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RevertApplication2.class,args);
    }
}
