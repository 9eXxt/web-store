package com.webstore.integration;

import com.webstore.integration.annotation.IntegrationTest;
import com.webstore.integration.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
@IntegrationTest
@Sql({"classpath:sql/test-data.sql"})
public class IntegrationTestBase {
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.6");
    @BeforeAll
    static void runContainer(){
        postgres.start();
    }
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }
}
