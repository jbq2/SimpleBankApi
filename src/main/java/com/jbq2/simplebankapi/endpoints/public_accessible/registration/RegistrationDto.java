package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDto {
    private String email;
    private String message;
}