package com.jbq2.simplebankapi.userpackage.pojo;

import com.jbq2.simplebankapi.interfaces.Queryable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Component
public class User implements Queryable {
    private Long id;
    private String email;
    private String password;
    private String created;
    private String updated;
}
