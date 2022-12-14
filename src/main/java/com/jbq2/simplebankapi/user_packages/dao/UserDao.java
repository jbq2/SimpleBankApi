package com.jbq2.simplebankapi.user_packages.dao;

import com.jbq2.simplebankapi.user_packages.interfaces.DataObjectAccessableById;
import com.jbq2.simplebankapi.user_packages.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/* data access object for User */
@Component
@Repository
public class UserDao implements DataObjectAccessableById<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = ((rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreated(rs.getDate("created").toString());
        user.setUpdated(rs.getDate("updated").toString());
        return user;
    });

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
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

    @Override
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

    @Override
    public User updateWithId(User user) {
        String sql = "UPDATE USERS " +
                "SET email = ?, password = ? " +
                "WHERE id = ?";
        try{
            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getPassword(),
                    user.getId());
            user = findByEmail(user.getEmail());
            return user;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    public boolean updateWithEmail(User user) {
        String sql = "UPDATE USERS " +
                "SET password = ? " +
                "WHERE email = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, user.getPassword(), user.getEmail());
            return rowsAffected != 0;
        }
        catch(RuntimeException e) {
            return false;
        }
    }

    @Override
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
