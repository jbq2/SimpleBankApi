package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import lombok.Data;

/**
 * Plain-old-Java-object that includes the details from the registration form in the web application.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class RegistrationForm {
    private String email;
    private String password;
    private String matching;

    /**
     * Initializes all 3 attributes of the RegistrationForm object.
     * @param email The user's entered email address.
     * @param password The user's entered, non-hashed password.
     * @param matching The user's password confirmation--also non-hashed.
     */
    public RegistrationForm(String email, String password, String matching) {
        this.email = email;
        this.password = password;
        this.matching = matching;
    }
}
