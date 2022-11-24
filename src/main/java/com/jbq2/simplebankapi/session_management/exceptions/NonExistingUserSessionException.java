package com.jbq2.simplebankapi.session_management.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonExistingUserSessionException extends RuntimeException {
    public NonExistingUserSessionException(String message){
        super(message);
    }
}
