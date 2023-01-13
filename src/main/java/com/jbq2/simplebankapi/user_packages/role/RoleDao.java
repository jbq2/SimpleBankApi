package com.jbq2.simplebankapi.user_packages.role;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class that directly interacts with the Role table of the database.
 * @author Joshua Quizon
 * @version 0.1
 */
@Repository
public class RoleDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Role> rowMapper;

    /**
     * Initializes the 2 attributes of the RoleDao class by injecting a JdbcTemplate object and configuring the RowMapper object.
     * @param jdbcTemplate Configuration for database connectivity that allows for sending queries to the database.
     */
    public RoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.rowMapper = ((rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            role.setCreated(rs.getDate("created").toString());
            role.setUpdated(rs.getDate("updated").toString());
            return role;
        });
    }

    /**
     * Sends a query to obtain a list of all Roles that are saved in the database.
     * @return Returns a List of role objects.
     */
    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Sends a query to save a Role to the database.
     * @param role Contains Role information that is to be saved.
     * @return Returns the saved Role if successful, and null otherwise.
     */
    public Role save(Role role) {
        String sql = "INSERT INTO roles (name) " +
                "VALUES (?)";
        try{
            jdbcTemplate.update(sql,
                    role.getName());
            return role;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Sends a query to delete a Role with a specific ID.
     * @param id The ID of the role.
     * @return Returns true if the Role was deleted, and false otherwise.
     */
    public Boolean delete(Long id) {
        String sql = "DELETE FROM roles " +
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
