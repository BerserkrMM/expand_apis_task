package com.example.expand_apis_task;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class TestConfig {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

    static {
        var l = new ArrayList<String>();
        l.add("55191:3306");
        mySQLContainer.setPortBindings(l);
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }
}
