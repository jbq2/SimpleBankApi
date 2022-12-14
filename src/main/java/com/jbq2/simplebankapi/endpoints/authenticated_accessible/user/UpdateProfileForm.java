package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateProfileForm {
    private String email;
    private String password;
    private String matching;
}
