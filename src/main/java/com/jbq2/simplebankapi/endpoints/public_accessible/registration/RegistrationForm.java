package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationForm {
    /* registration form as of 11-18-2022 will contain the 3 fields below */
    private String email;
    private String password;
    private String matching;
}
