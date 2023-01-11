package com.jbq2.simplebankapi.user_packages.user;

import com.jbq2.simplebankapi.user_packages.user_role.UserRoleDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Email not found");
        }

        user.setUserRoleDao(userRoleDao);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.getAuthorities()
        );
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public User saveUser(User user){
        return userDao.save(user);
    }

    public User updateExistingUser(User user, String oldEmail) {
        return userDao.updateExistingUser(user, oldEmail);
    }

    public Boolean deleteUser(Long id){
        return userDao.delete(id);
    }
}
