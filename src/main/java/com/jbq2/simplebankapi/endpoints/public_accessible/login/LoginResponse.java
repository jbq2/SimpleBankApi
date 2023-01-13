package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.Data;

/**
 * Plain-old-Java-object that includes all details that must be returned to the web application upon processing a user login request.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class LoginResponse {
    private String jwt;
    private String message;

    /**
     * Initializes the 2 attributes of the LoginResponse object.
     * @param jwt The JSON web token of the user.
     * @param message A developer written message.
     */
    public LoginResponse(String jwt, String message) {
        this.jwt = jwt;
        this.message = message;
    }
}
