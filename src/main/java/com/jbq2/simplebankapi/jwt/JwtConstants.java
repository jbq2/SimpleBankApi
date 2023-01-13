package com.jbq2.simplebankapi.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component that includes the key for decoding JSON web tokens.
 * @author Joshua Quizon
 * @version 0.1
 */
@Component
public class JwtConstants {

    /**
     * Injected with the "jwt.secret" attribute in the application.properties file.
     */
    @Value("${jwt.secret}")
    public String key;
}
