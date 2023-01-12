package com.jbq2.simplebankapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * This class configures database connectivity details.
 * @author Joshua Quizon
 * @version 0.1
 */
@Configuration
public class DbConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * This method creates a bean of type DataSource which holds the configuration that connect to the database.
     * @return Returns an object of type DataSource which is implicitly used for querying the database.
     */
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(driverClassName);
        source.setUrl(url);
        source.setUsername(username);
        source.setPassword(password);
        return source;
    }

    /**
     * This method creates a bean of type JdbcTemplate.
     * @return Returns an object of type JdbcTemplate which is used by various data access objects.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DataSource dataSource = dataSource();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
