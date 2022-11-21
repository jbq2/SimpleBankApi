package com.jbq2.simplebankapi.userpackage.pojo;

import com.jbq2.simplebankapi.interfaces.Queryable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class UserRole implements Queryable {
    Long id;
    Long user_id;
    Long role_id;
    String created;
    String updated;
}
