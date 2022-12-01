package com.jbq2.simplebankapi.user_packages.service;

import com.jbq2.simplebankapi.user_packages.dao.UserDao;
import com.jbq2.simplebankapi.user_packages.dao.UserRoleDao;
import com.jbq2.simplebankapi.user_packages.pojo.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/* implements UserDetailsService for using Spring Security features */
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /* get the user by email */
        User user = userDao.findByEmail(email);

        /* return null if user is null */
        if(user == null){
            throw new UsernameNotFoundException("Email not found");
        }

        /* if user is not null, injecting userRoleDao is safe */
        user.setUserRoleDao(userRoleDao);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.getAuthorities()
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
