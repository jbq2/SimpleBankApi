package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileResponse {
    private String email;
    private String message;
    private String jwt;
}
