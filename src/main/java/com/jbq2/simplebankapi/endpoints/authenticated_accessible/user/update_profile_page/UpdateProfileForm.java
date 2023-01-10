package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateProfileForm {
    private String email;
    private String oldEmail;
    private String oldPassword;
    private String password;
    private String matching;
    private String jwt;
}
