package com.jbq2.simplebankapi.user_packages.user_role;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Plain-old-Java-object that matches the attributes of the User_Role table in the database and implements QueryableById.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
@NoArgsConstructor
public class UserRole implements QueryableById {
    private Long id;
    private Long user_id;
    private Long role_id;
    private String created;
    private String updated;
}
