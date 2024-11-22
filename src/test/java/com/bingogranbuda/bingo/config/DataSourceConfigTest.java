package com.bingogranbuda.bingo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestConfiguration
public class DataSourceConfigTest {

    @Bean
    public static PostgreSQLContainer<?> postgresContainer(){
        try(PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres")
                .withUsername("userTest")
                .withPassword("passTest")
                .withDatabaseName("db_test")) {

            container.start();
            return container;
        }
    }

    @Bean
    public HikariDataSource hikariDatasource(PostgreSQLContainer<?> postgresContainer){
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .url(postgresContainer.getJdbcUrl())
                .username(postgresContainer.getUsername())
                .password(postgresContainer.getPassword())
                .driverClassName(postgresContainer.getDriverClassName())
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource){
        return new JdbcTemplate(hikariDataSource);
    }
}
