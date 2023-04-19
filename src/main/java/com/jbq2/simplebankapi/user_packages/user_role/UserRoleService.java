package com.jbq2.simplebankapi.user_packages.user_role;

import com.jbq2.simplebankapi.user_packages.role.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides methods that use business logic to get user-role relationships.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class UserRoleService {
    private final UserRoleDao userRoleDao;

    /**
     * Initializes the UserRoleDao attribute of the UserRoleService class by injecting a UserRoleDao object.
     * @param userRoleDao Directly sends queries to the User_Role table of the database.
     */
    public UserRoleService(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * Gets the roles of a given user.
     * @param id The ID of the user.
     * @return Returns a List of RoleEnum objects which are the authorities belonging to the user.
     */
    public List<RoleEnum> getUserRoles(Long id){
        return userRoleDao.findAllByUserId(id)
                .stream()
                .map(userRole -> (userRole.getRole_id() == 1) ? RoleEnum.ADMIN : RoleEnum.USER)
                .collect(Collectors.toList());
    }

    /**
     * Saves a user-role relationship.
     * @param userRole Contains the information required to save a user-role relationship to the database.
     * @return Returns the saved UserRole object.
     */
    public UserRole saveUserRole(UserRole userRole){
        return userRoleDao.save(userRole);
    }
}
