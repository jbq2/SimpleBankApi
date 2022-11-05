package com.jbq2.simplebankapi.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserRole {
    Long id;
    Long user_id;
    Long role_id;
    String created;
    String updated;
}
