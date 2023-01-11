package com.jbq2.simplebankapi.user_packages.user_role;

import com.jbq2.simplebankapi.user_packages.role.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleService {
    private final UserRoleDao userRoleDao;

    public List<RoleEnum> getUserRoles(Long id){
        List<RoleEnum> roles = new ArrayList<>();
        for(UserRole userRole : userRoleDao.findAllByUserId(id)){
            if (userRole.getRole_id() == 1) {
                roles.add(RoleEnum.ADMIN);
            }
            else{
                roles.add(RoleEnum.USER);
            }
        }
        return roles;
    }

    public UserRole saveUserRole(UserRole userRole){
        return userRoleDao.save(userRole);
    }
}
