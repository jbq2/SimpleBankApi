package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import lombok.Data;

/**
 * This class is a normal Java object that includes all details that must be sent in as a response to the web applications request.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class RegistrationResponse {
    private String email;
    private String message;

    /**
     * This constructor initializes the 2 attributes of the RegistrationResponse object.
     * @param email A string depicting the email address the user registered with.
     * @param message A string depicting a success message.
     */
    public RegistrationResponse(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
