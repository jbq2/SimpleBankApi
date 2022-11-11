package com.jbq2.simplebankapi.pojo;

import com.jbq2.simplebankapi.pojo.interfaces.Queryable;
import lombok.Data;

@Data
public class Role implements Queryable {
    private Long id;
    private String name;
    private String created;
    private String updated;
}
