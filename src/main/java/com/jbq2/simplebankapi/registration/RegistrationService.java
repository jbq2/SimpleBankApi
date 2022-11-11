package com.jbq2.simplebankapi.registration;

import com.jbq2.simplebankapi.userpackage.dao.UserDao;
import com.jbq2.simplebankapi.userpackage.dao.UserRoleDao;
import com.jbq2.simplebankapi.userpackage.pojo.RoleEnum;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import com.jbq2.simplebankapi.userpackage.pojo.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {
    User user;
    UserRole userRole;
    UserDao userDao;
    UserRoleDao userRoleDao;
    Pattern pattern;
    Matcher matcher;
    public Boolean validateAndSave(Registration registration){
        pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$");
        matcher = pattern.matcher(registration.getEmail());
        if(!matcher.find()){
            return false;
        }
        user.setEmail(registration.getEmail());
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(registration.getPassword());
        if(!matcher.find()){
            return false;
        }
        if(!registration.getPassword().equals(registration.getMatching())){
            return false;
        }
        user.setPassword(registration.getPassword());
        user = userDao.save(user);
        if(user == null){
            return false;
        }
        userRole.setUser_id(user.getId());
        userRole.setRole_id(RoleEnum.USER.getValue());
        if(userRoleDao.save(userRole) == null){
            return false;
        }
        return true;
    }
}
