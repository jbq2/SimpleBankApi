package com.jbq2.simplebankapi.user_packages.pojo;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import com.jbq2.simplebankapi.user_packages.dao.UserRoleDao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User implements QueryableById, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String created;
    private String updated;

    /* userRoleDao will be injected via setter */
    private UserRoleDao userRoleDao;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> rolesAsStrings = userRoleDao.findAllRoleNamesById(id);

        /* populating authorities list with new SimpleGrantedAuthority objects */
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
