package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class UpdateProfileResponse {
    String email;
    String message;
    String jwt;
}
