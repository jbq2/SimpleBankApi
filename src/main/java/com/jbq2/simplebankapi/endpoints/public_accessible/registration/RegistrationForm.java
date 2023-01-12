package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import lombok.Data;

/**
 * This class is a normal Java object includes the details from the registration form in the web application.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class RegistrationForm {
    private String email;
    private String password;
    private String matching;

    /**
     * This constructor initializes all 3 attributes of the RegistrationForm object.
     * @param email A string depicting the user's entered email address.
     * @param password A string depicting the user's entered, non-hashed password.
     * @param matching A string depicting the user's password confirmation--also non-hashed.
     */
    public RegistrationForm(String email, String password, String matching) {
        this.email = email;
        this.password = password;
        this.matching = matching;
    }
}
