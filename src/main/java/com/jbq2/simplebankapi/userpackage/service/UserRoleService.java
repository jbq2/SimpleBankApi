package com.jbq2.simplebankapi.userpackage.service;

import com.jbq2.simplebankapi.userpackage.dao.UserRoleDao;
import com.jbq2.simplebankapi.userpackage.pojo.Role;
import com.jbq2.simplebankapi.userpackage.pojo.RoleEnum;
import com.jbq2.simplebankapi.userpackage.pojo.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
/* so far only needed for registration */
/* TODO: implement other basic service methods */
public class UserRoleService {
    private final UserRoleDao userRoleDao;

    /* gets the roles of a specific user */
    public List<RoleEnum> getUserRoles(Long id){
        List<RoleEnum> roles = new ArrayList<>();
        for(UserRole userRole : userRoleDao.findAllByUserId(id)){
            /* NOTE: this will be changed due to the possibility of adding more roles */
            if (userRole.getRole_id() == 1) {
                roles.add(RoleEnum.ADMIN);
            }
            else{
                roles.add(RoleEnum.USER);
            }
        }
        return roles;
    }

    /* TODO: find all user roles method */

    /* TODO: find user role by id */

    /* saves user role to database */
    public UserRole saveUserRole(UserRole userRole){
        return userRoleDao.save(userRole);
    }

    /* TODO: update user role method */

    /* TODO: delete user role method */
}
