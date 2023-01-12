package com.jbq2.simplebankapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class that creates is responsible for initializing the BCryptPasswordEncoder bean.
 * @author Joshua Quizon
 * @version 0.1
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a BCryptPasswordEncoder bean which is used for encrypting passwords before storing them in the database.
     * @return Returns BCryptPasswordEncoder bean which encrypts strings.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
