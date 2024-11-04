package com.bingogranbuda.bingo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@Profile("test")
@TestConfiguration
public class DataSourceConfigTest {

    @Bean
    @ConfigurationProperties("app.datasource.main")
    public HikariDataSource testHikariDatasource(){
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate testJdbcTemplate(HikariDataSource testHikariDatasource){
        return new JdbcTemplate(testHikariDatasource);
    }
}
