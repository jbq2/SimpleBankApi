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
public class UserRoleService {
    private final UserRoleDao userRoleDao;

    public List<RoleEnum> getUserRoles(Long id){
        List<UserRole> rolesRaw = userRoleDao.findAllByUserId(id);
        List<RoleEnum> roles = new ArrayList<>();
        for(UserRole userRole : rolesRaw){
            if (userRole.getRole_id() == 1) {
                roles.add(RoleEnum.ADMIN);
            }
            else{
                roles.add(RoleEnum.USER);
            }
        }
        return roles;
    }
}
