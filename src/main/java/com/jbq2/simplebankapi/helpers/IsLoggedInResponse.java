package com.jbq2.simplebankapi.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Plain-old-Java-object that contains the details to be sent back to the web application upon checking if a user is logged in.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class IsLoggedInResponse {
    private boolean loggedIn;
    private String updatedJwt;

    /**
     * Initializes the 2 attributes of the IsLoggedInResponse class.
     * @param loggedIn True if the user is logged in, and false otherwise.
     * @param updatedJwt The updated JSON web token of the user.
     */
    public IsLoggedInResponse(boolean loggedIn, String updatedJwt) {
        this.loggedIn = loggedIn;
        this.updatedJwt = updatedJwt;
    }
}
