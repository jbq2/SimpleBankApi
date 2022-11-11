package com.jbq2.simplebankapi.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String created;
    private String updated;
}
