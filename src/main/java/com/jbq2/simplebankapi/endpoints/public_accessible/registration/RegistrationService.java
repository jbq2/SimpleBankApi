package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.*;
import com.jbq2.simplebankapi.user_packages.pojo.RoleEnum;
import com.jbq2.simplebankapi.user_packages.pojo.User;
import com.jbq2.simplebankapi.user_packages.pojo.UserRole;
import com.jbq2.simplebankapi.user_packages.service.UserRoleService;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {

    /* userpackage objects needed */
    private final UserService userService;
    private final UserRoleService userRoleService;

    /* Pattern and Matcher objects for regex validation */

    public String validateAndSave(Registration registration){
        User user = new User();
        UserRole userRole = new UserRole();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /* validates email */
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = pattern.matcher(registration.getEmail());
        if(!matcher.find()){
            throw new InvalidEmailException("Email is incorrectly formatted.");
        }
        user.setEmail(registration.getEmail());

        /* validates password */
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(registration.getPassword());
        if(!matcher.find()){
            throw new InvalidPasswordException("Password does not meet requirements (At least 8 characters, no spaces, and contains at least 1 letter, 1 digit, and 1 special character).");
        }
        if(!registration.getPassword().equals(registration.getMatching())){
            throw new NonMatchingPasswordsException("Password confirmation does not match the password.");
        }
        /* encode password before saving to db */
        user.setPassword(encoder.encode(registration.getPassword()));

        /* DataAccessException if email UNIQUE constraint is violated */
        user = userService.saveUser(user);
        if(user == null){
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        /* saves user_role, throws error if non unique */
        userRole.setUser_id(user.getId());
        userRole.setRole_id(RoleEnum.USER.getValue());
        if(userRoleService.saveUserRole(userRole) == null){
            throw new UserAlreadyHasRoleException("User-role combination already exists.");
        }
        return registration.getEmail();
    }
}
