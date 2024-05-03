package com.chaabiamal.springboot_cassandra_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.chaabiamal.springboot_cassandra_demo.model"})
public class SpringBootCassandraDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCassandraDemoApplication.class, args);
    }
}
