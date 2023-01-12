package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.Data;

/**
 * This is a normal Java class that includes all details that must be returned to the web application upon processing a user login request.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class LoginResponse {
    private String jwt;
    private String message;

    /**
     * This constructor initializes the 2 attributes of the LoginResponse object.
     * @param jwt A String depicting the JSON web token of the user.
     * @param message A String depicting a developer written message.
     */
    public LoginResponse(String jwt, String message) {
        this.jwt = jwt;
        this.message = message;
    }
}
