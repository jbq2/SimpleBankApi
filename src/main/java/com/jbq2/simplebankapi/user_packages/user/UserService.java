package com.jbq2.simplebankapi.user_packages.user;

import com.jbq2.simplebankapi.user_packages.user_role.UserRoleDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides methods that gather and save user details from and to the database and implements UserDetailsService.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    /**
     * Initializes the 2 attributes of the UserService class by injecting UserDao and UserRoleDao objects.
     * @param userDao Directly sends queries to the User table of the database.
     * @param userRoleDao Directly sends queries to the User_Role table of the database.
     */
    public UserService(UserDao userDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
    }

    /**
     * Gets a UserDetails object given an email address.
     * @param email The email address of the user.
     * @return Returns a UserDetail object containing user details.
     * @throws UsernameNotFoundException Throws a UsernameNotFoundException when the email address does not exist in the database.
     */
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

    /**
     * Gets a List of all users saved in the database.
     * @return Returns a List of User objects from the database.
     */
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    /**
     * Saves a new user to the database.
     * @param user Contains all the details of the new user.
     * @return Returns the saved User object.
     */
    public User saveUser(User user){
        return userDao.save(user);
    }

    /**
     * Updates an existing user in the database.
     * @param user Contains the new details of the existing user.
     * @param oldEmail The old email address of the user.
     * @return Returns the updated User object.
     */
    public User updateExistingUser(User user, String oldEmail) {
        return userDao.updateExistingUser(user, oldEmail);
    }

    /**
     * Deletes a user from the database.
     * @param id ID of the user.
     * @return Returns true if the user was deleted, and false otherwise.
     */
    public Boolean deleteUser(Long id){
        return userDao.delete(id);
    }
}
