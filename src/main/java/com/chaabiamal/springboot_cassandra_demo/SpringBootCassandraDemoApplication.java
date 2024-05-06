package com.chaabiamal.springboot_cassandra_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.chaabiamal.springboot_cassandra_demo.Configuration"})
public class SpringBootCassandraDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCassandraDemoApplication.class, args);
    }

}