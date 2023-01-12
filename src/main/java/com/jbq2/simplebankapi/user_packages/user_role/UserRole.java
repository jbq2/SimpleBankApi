package com.jbq2.simplebankapi.user_packages.user_role;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRole implements QueryableById {
    Long id;
    Long user_id;
    Long role_id;
    String created;
    String updated;
}
