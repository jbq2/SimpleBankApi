package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Plain-old-Java-object that contains all information that needs to be sent back to the web application upon sending
 * a request to update a user's profile.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
@AllArgsConstructor
public class UpdateProfileResponse {
    private String email;
    private String message;
    private String jwt;
}
