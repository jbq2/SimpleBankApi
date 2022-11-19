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
import java.util.List;

@Service
@AllArgsConstructor
/* implements UserDetailsService for using Spring Security features */
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
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

    /* finds a user with id */
    public User getUserById(Long id){
        /* TODO: should do the same thing as loadUserByUserName */
        return userDao.findById(id);
    }

    /* gets all users */
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    /* saves new user */
    public User saveUser(User user){
        return userDao.save(user);
    }

    /* updates user info */
    public User updateUser(User user){
        return userDao.update(user);
    }

    /* deletes user */
    public Boolean deleteUser(Long id){
        return userDao.delete(id);
    }
}
