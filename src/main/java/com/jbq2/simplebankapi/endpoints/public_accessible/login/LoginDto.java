package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String session_id;
    private String message;
}
