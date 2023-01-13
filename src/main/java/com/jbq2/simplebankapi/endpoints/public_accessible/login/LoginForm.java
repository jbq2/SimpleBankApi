package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.Data;

/**
 * Plain-old-Java-object consisting of the necessary fields from the login form in the web application.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class LoginForm {
    private String email;
    private String password;

    /**
     * Initializes the 2 attributes of the LoginForm object.
     * @param email The user's entered email address.
     * @param password The user's entered, non-hashed password.
     */
    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
