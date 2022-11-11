package com.jbq2.simplebankapi.pojo;

import com.jbq2.simplebankapi.pojo.interfaces.Queryable;
import lombok.Data;

@Data
public class User implements Queryable {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String created;
    private String updated;
}
