package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.restservice.repository.UserRepository;

@SpringBootApplication
public class RestServiceApplication {

    @Autowired
	UserRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
