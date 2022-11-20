package com.jbq2.simplebankapi.registration;

import com.jbq2.simplebankapi.userpackage.dao.UserRoleDao;
import com.jbq2.simplebankapi.userpackage.pojo.RoleEnum;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import com.jbq2.simplebankapi.userpackage.pojo.UserRole;
import com.jbq2.simplebankapi.userpackage.service.UserRoleService;
import com.jbq2.simplebankapi.userpackage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {

    /* userpackage objects needed */
    private User user;
    private UserRole userRole;
    private final UserService userService;
    private final UserRoleService userRoleService;

    /* Pattern and Matcher objects for regex validation */

    public RegistrationStatus validateAndSave(Registration registration){
        /* validates email */
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$");
        Matcher matcher = pattern.matcher(registration.getEmail());
        if(!matcher.find()){
            return RegistrationStatus.FAIL_BAD_EMAIL;
        }
        user.setEmail(registration.getEmail());

        /* validates password */
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(registration.getPassword());
        if(!matcher.find()){
            return RegistrationStatus.FAIL_BAD_PASSWORD;
        }
        if(!registration.getPassword().equals(registration.getMatching())){
            return RegistrationStatus.FAIL_BAD_MATCH;
        }
        user.setPassword(registration.getPassword());

        /* DataAccessException if email UNIQUE constraint is violated */
        user = userService.saveUser(user);
        if(user == null){
            return RegistrationStatus.FAIL_EMAIL_EXISTS;
        }

        /* saves user_role, throws error if non unique */
        userRole.setUser_id(user.getId());
        userRole.setRole_id(RoleEnum.USER.getValue());
        return (userRoleService.saveUserRole(userRole) != null) ? RegistrationStatus.SUCCESS : RegistrationStatus.FAIL_BAD_ROLE_SAVE;
    }
}
