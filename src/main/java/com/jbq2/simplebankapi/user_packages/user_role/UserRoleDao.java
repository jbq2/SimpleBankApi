package com.jbq2.simplebankapi.user_packages.user_role;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class that directly sends queries to the User_Role table in the database.
 * @author Joshua Quizon
 * @version 0.1
 */
@Repository
public class UserRoleDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<UserRole> userRoleRowMapper;

    private final RowMapper<String> stringRowMapper;

    /**
     * Initializes the 3 attributes of the UserRoleDao class by injecting a JdbcTemplate object and configuring the 2 RowMapper objects.
     * @param jdbcTemplate
     */
    public UserRoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.userRoleRowMapper = ((rs, rowNum) -> {
            UserRole userRole = new UserRole();
            userRole.setId(rs.getLong("id"));
            userRole.setUser_id(rs.getLong("user_id"));
            userRole.setRole_id(rs.getLong("role_id"));
            userRole.setCreated(rs.getDate("created").toString());
            userRole.setUpdated(rs.getDate("updated").toString());
            return userRole;
        });

        this.stringRowMapper = ((rs, rowNum) -> rs.getString("name"));
    }

    /**
     * Gets a list of user-role relationships from the database.
     * @return Returns a List of UserRole objects if there exist any, and null otherwise.
     */
    public List<UserRole> findAll() {
        String sql = "SELECT * FROM user_role";
        try{
            return jdbcTemplate.query(sql, userRoleRowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Gets the roles of a user in the database.
     * @param id The ID of the user.
     * @return Returns a List of Strings which are the authorities of the user.
     */
    public List<String> findAllRoleNamesById(Long id){
        String sql = "SELECT roles.name " +
                "FROM roles INNER JOIN user_role ON roles.id = user_role.role_id " +
                "INNER JOIN users ON users.id = user_role.user_id " +
                "WHERE users.id = ?;";

        try{
            return jdbcTemplate.query(sql, stringRowMapper, id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Gets a list of user-role relationships for a specific user in the database.
     * @param id The ID of the user.
     * @return Returns a list of UserRole objects if there exist any, and null otherwise.
     */
    public List<UserRole> findAllByUserId(Long id){
        String sql = "SELECT * FROM user_role " +
                "WHERE user_id = ?";
        try{
            return jdbcTemplate.query(sql, userRoleRowMapper, id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Saves a user-role relationship to the database
     * @param userRole Contains the necessary information to save a user-role to the database.
     * @return Returns the saved user-role relationship if successful, and null otherwise.
     */
    public UserRole save(UserRole userRole) {
        String sql = "INSERT INTO user_role (user_id, role_id) " +
                "VALUES (?, ?)";
        try{
            jdbcTemplate.update(sql,
                    userRole.getUser_id(),
                    userRole.getRole_id());
            return userRole;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Updates an existing user-role relationship in the database.
     * @param userRole Contains the new user-role relationship details.
     * @return Returns the updated UserRole object if successful, and null otherwise.
     */
    public UserRole updateWithId(UserRole userRole) {
        String sql = "UPDATE user_role " +
                "SET user_id = ?, role_id =? " +
                "WHERE id = ?";
        try{
            jdbcTemplate.update(sql,
                    userRole.getUser_id(),
                    userRole.getRole_id(),
                    userRole.getId());
            return userRole;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Deletes a user-role relationship from the database given an ID.
     * @param id ID of the user-role relationship.
     * @return Returns true if the deletion was successful, and false otherwise.
     */
    public Boolean delete(Long id) {
        String sql = "DELETE FROM user_role " +
                "WHERE id = ?";
        try{
            jdbcTemplate.update(sql, id);
            return true;
        }
        catch(DataAccessException e){
            return false;
        }
    }
}
