package com.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartContactManagerApplication {

	

    public static void main(String[] args) {
    	
    	System.setProperty("spring.datasource.username", "root");
        System.setProperty("spring.datasource.password", "root");

        SpringApplication.run(SmartContactManagerApplication.class, args);
   }
}
