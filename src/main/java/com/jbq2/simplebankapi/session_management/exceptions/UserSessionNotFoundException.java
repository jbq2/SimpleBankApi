package com.jbq2.simplebankapi.session_management.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserSessionNotFoundException extends RuntimeException {
    public UserSessionNotFoundException(String message){
        super(message);
    }
}
