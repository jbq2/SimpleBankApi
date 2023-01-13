package com.jbq2.simplebankapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configures database connectivity details.
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
     * Creates configures a DataSource object which holds the information to connect to the database.
     * @return Returns a DataSource bean which is used in the jdbcTemplate() method.
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
     * Creates a bean JdbcTemplate bean.
     * @return Returns a JdbcTemplate object which is used by various data access objects, allowing for sending queries to the database.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DataSource dataSource = dataSource();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
