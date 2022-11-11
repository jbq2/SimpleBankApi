package com.jbq2.simplebankapi.pojo;

import com.jbq2.simplebankapi.pojo.interfaces.Queryable;
import lombok.Data;

@Data
public class UserRole implements Queryable {
    Long id;
    Long user_id;
    Long role_id;
    String created;
    String updated;
}
