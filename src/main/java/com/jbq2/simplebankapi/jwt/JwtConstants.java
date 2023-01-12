package com.jbq2.simplebankapi.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstants {

    @Value("${jwt.secret}")
    public String key;
}
