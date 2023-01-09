package com.jbq2.simplebankapi.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsLoggedInResponse {
    private boolean loggedIn;
    private String updatedJwt;
}
