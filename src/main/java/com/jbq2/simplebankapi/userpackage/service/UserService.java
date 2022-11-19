package com.jbq2.simplebankapi.userpackage.service;

import com.jbq2.simplebankapi.userpackage.dao.UserDao;
import com.jbq2.simplebankapi.userpackage.pojo.RoleEnum;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final UserRoleService userRoleService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        // below will be changed, for now they are all basically set to true
        boolean enabled = true;
        boolean accountExpired = false;
        boolean credentialsExpired = false;
        boolean accountLocked = false;
        if(user == null){
            return null;
        }
        Collection<RoleEnum> roles = userRoleService.getUserRoles(user.getId());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                enabled, !accountExpired, !credentialsExpired, !accountLocked,
                roles
        );
    }
}
