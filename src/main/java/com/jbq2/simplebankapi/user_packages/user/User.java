package com.jbq2.simplebankapi.user_packages.user;

import com.jbq2.simplebankapi.user_packages.interfaces.QueryableById;
import com.jbq2.simplebankapi.user_packages.user_role.UserRoleDao;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Plain-old-Java-object that matches the attributes of the User table in the database and implements the QueryableById and UserDetails interfaces.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
@NoArgsConstructor
public class User implements QueryableById, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String created;
    private String updated;
    private UserRoleDao userRoleDao;

    /**
     * Gets a Collection of GrantedAuthorities of the User from the database.
     * @return Returns a Collection of GrantedAuthorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> rolesAsStrings = userRoleDao.findAllRoleNamesById(id);
        for(String role : rolesAsStrings){
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    /**
     * Gets the email of the user.
     * @return Returns a String that is the email of the user.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * As of version 0.1, accounts are always non-expired.
     * @return Returns true to show that the account is non-expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * As of version 0.1, accounts are always non-locked.
     * @return Returns true to show that the account is non-locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * As of version 0.1, credentials are always non-expired.
     * @return Returns true to show that the user's credentials are non-expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * As of version 0.1, accounts are always enabled.
     * @return Returns true to show that the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
