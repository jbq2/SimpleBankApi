package com.jbq2.simplebankapi.user_packages.role;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import lombok.Data;

@Data
public class Role implements QueryableById {
    private Long id;
    private String name;
    private String created;
    private String updated;
}
