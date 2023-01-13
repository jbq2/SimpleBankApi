package com.jbq2.simplebankapi.user_packages.role;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Plain-old-Java-object that implements the QueryableById interface and includes all attributes of the Role table in the database.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
@NoArgsConstructor
public class Role implements QueryableById {
    private Long id;
    private String name;
    private String created;
    private String updated;
}
