package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import com.jbq2.simplebankapi.user_packages.role.RoleEnum;
import com.jbq2.simplebankapi.user_packages.user.User;
import com.jbq2.simplebankapi.user_packages.user_role.UserRole;
import com.jbq2.simplebankapi.user_packages.user_role.UserRoleService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserRoleService userRoleService;


    public String validateAndSave(RegistrationForm registrationForm){
        User user = new User();
        UserRole userRole = new UserRole();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /* validates email */
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = pattern.matcher(registrationForm.getEmail());
        if(!matcher.find()){
            throw new RuntimeException("Email is incorrectly formatted.");
        }
        user.setEmail(registrationForm.getEmail());

        /* validates password */
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(registrationForm.getPassword());
        if(!matcher.find()){
            throw new RuntimeException("Password does not meet requirements (At least 8 characters, no spaces, and contains at least 1 letter, 1 digit, and 1 special character).");
        }
        if(!registrationForm.getPassword().equals(registrationForm.getMatching())){
            throw new RuntimeException("Password confirmation does not match the password.");
        }
        /* encode password before saving to db */
        user.setPassword(encoder.encode(registrationForm.getPassword()));

        /* DataAccessException if email UNIQUE constraint is violated */
        user = userService.saveUser(user);
        if(user == null){
            throw new RuntimeException("Email already exists.");
        }

        /* saves user_role, throws error if non unique */
        userRole.setUser_id(user.getId());
        userRole.setRole_id(RoleEnum.USER.getValue());
        if(userRoleService.saveUserRole(userRole) == null){
            throw new RuntimeException("User-role combination already exists.");
        }
        return registrationForm.getEmail();
    }
}
