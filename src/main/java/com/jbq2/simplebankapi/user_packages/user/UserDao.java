package com.jbq2.simplebankapi.user_packages.user;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class that directly interacts with the User table of the database.
 * @author Joshua Quizon
 * @version 0.1
 */
@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    /**
     * Initializes the 2 attributes of the UserDao object by injecting the JdbcTemplate object and configuring the RowMapper.
     * @param jdbcTemplate Configuration for database connectivity that allows for sending queries to the database.
     */
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        rowMapper = ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setCreated(rs.getDate("created").toString());
            user.setUpdated(rs.getDate("updated").toString());
            return user;
        });
    }

    /**
     * Gets a List of all users saved in the database.
     * @return Returns the List of all User objects in the database if there are any, and null otherwise.
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Gets a user from the database given an ID.
     * @param id The ID of the user.
     * @return Returns the User object with the given ID if found, and null otherwise.
     */
    public User findById(Long id) {
        String sql = "SELECT * FROM USERS " +
                "WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * Gets a user from the database given an email address.
     * @param email The email address of the user.
     * @return Returns the User object if the email exists in the database, and null otherwise.
     */
    public User findByEmail(String email){
        String sql = "SELECT * FROM users " +
                "WHERE email = ?";
        try{
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Saves user information to the database.
     * @param user The User Object containing the necessary details that will be saved to the User table.
     * @return Returns the saved User object if successful, and null othewise.
     */
    public User save(User user) {
        String sql = "INSERT INTO USERS (email, password) " +
                "VALUES (?, ?)";

        try{
            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getPassword());
            user = findByEmail(user.getEmail());
            return user;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    /**
     * Updates a user that already exists in the database given the user's old email address.
     * @param user The User object containing the new user details.
     * @param oldEmail The old email address of the user.
     * @return
     */
    public User updateExistingUser(User user, String oldEmail) {
        String sql = "UPDATE USERS " +
                "SET email = ?, password = ? " +
                "WHERE email = ?";

        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), oldEmail);
        return user;
    }

    /**
     * Deletes a user from the database given an ID.
     * @param id The ID of the user.
     * @return Returns true if the deletion was successful, and false otherwise.
     */
    public Boolean delete(Long id) {
        String sql = "DELETE FROM users " +
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
