package com.jbq2.simplebankapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class is a configuration class that creates is responsible for initializing the BCryptPasswordEncoder bean.
 * @author Joshua Quizon
 * @version 0.1
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * This method creates a bean of type BCryptPasswordEncoder which is used for encrypts passwords before storing them in the database.
     * @return Returns an object of type BCryptPasswordEncoder which encrypts strings.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
