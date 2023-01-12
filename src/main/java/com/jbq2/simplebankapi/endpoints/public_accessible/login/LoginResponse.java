package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private String message;
}
