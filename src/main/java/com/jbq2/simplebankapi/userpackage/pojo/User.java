package com.jbq2.simplebankapi.userpackage.pojo;

import com.jbq2.simplebankapi.interfaces.Queryable;
import com.jbq2.simplebankapi.userpackage.dao.UserRoleDao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User implements Queryable, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String created;
    private String updated;
    private UserRoleDao userRoleDao;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> rolesAsStrings = userRoleDao.findAllRoleNamesById(id);
        for(String role : rolesAsStrings){
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
