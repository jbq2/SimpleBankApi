package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    /*
    * login response will contain the user's session id, authorities, and a developer message
    * */
    private String session_id;
    private String email;
    private List<String> authorities;
    private String message;
}
