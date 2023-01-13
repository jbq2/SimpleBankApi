package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import com.jbq2.simplebankapi.user_packages.role.RoleEnum;
import com.jbq2.simplebankapi.user_packages.user.User;
import com.jbq2.simplebankapi.user_packages.user_role.UserRole;
import com.jbq2.simplebankapi.user_packages.user_role.UserRoleService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service that is responsible for handling the business logic for registering a new user.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class RegistrationService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Initializes all 3 attributes of the RegistrationService object.
     * @param userService Provides services for gathering and saving user details from and to the database.
     * @param userRoleService Provides services for gathering and saving user-role relationships from and to the database.
     * @param bCryptPasswordEncoder Encrypts and decrypts passwords.
     */
    public RegistrationService(UserService userService, UserRoleService userRoleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Validates the form field inputs, and if they are valid, saves the user details.
     * @param registrationForm Holds the user's entered registration information.
     * @return Returns a String depicting the email address the user registered with.
     * @throws RuntimeException Throws a RuntimeException when the forms fields are invalid or if the entered email address already exists in the database.
     */
    public String validateAndSave(RegistrationForm registrationForm){
        User user = new User();
        UserRole userRole = new UserRole();

        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = pattern.matcher(registrationForm.getEmail());
        if(!matcher.find()){
            throw new RuntimeException("Email is incorrectly formatted.");
        }
        user.setEmail(registrationForm.getEmail());

        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(registrationForm.getPassword());
        if(!matcher.find()){
            throw new RuntimeException("Password does not meet requirements (At least 8 characters, no spaces, and contains at least 1 letter, 1 digit, and 1 special character).");
        }
        if(!registrationForm.getPassword().equals(registrationForm.getMatching())){
            throw new RuntimeException("Password confirmation does not match the password.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(registrationForm.getPassword()));

        user = userService.saveUser(user);
        if(user == null){
            throw new RuntimeException("Email already exists.");
        }

        userRole.setUser_id(user.getId());
        userRole.setRole_id(RoleEnum.USER.getValue());
        if(userRoleService.saveUserRole(userRole) == null){
            throw new RuntimeException("User-role combination already exists.");
        }

        return registrationForm.getEmail();
    }
}
