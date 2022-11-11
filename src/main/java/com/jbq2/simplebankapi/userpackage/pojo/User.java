package com.jbq2.simplebankapi.userpackage.pojo;

import com.jbq2.simplebankapi.interfaces.Queryable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User implements Queryable {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String created;
    private String updated;
}
