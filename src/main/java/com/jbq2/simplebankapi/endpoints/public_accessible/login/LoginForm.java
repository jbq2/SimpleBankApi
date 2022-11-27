package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginForm {
    private String email;
    private String password;
}
