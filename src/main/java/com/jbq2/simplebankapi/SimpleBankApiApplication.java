package com.jbq2.simplebankapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * SpringBootApplication driver class
 * @author Joshua Quizon
 * @version 0.1
 */
@SpringBootApplication
public class SimpleBankApiApplication {

    /**
     * Main function of the API.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(SimpleBankApiApplication.class, args);
    }
}
