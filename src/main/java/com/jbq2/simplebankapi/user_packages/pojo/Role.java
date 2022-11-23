package com.jbq2.simplebankapi.user_packages.pojo;

import com.jbq2.simplebankapi.user_packages.interfaces.Queryable;
import lombok.Data;

@Data
public class Role implements Queryable {
    private Long id;
    private String name;
    private String created;
    private String updated;
}
