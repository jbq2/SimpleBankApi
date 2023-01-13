package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Plain-old-Java-object that contains all fields from the update profile form as well as other information that allows for
 * updating user details in the database.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
@NoArgsConstructor
public class UpdateProfileForm {
    private String email;
    private String oldEmail;
    private String oldPassword;
    private String password;
    private String matching;
    private String jwt;
}
