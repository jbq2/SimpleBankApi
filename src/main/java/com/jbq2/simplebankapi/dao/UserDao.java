package com.jbq2.simplebankapi.dao;

import com.jbq2.simplebankapi.dao.Dao;
import com.jbq2.simplebankapi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserDao implements Dao<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = ((rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
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

    @Override
    public User save(User user) {
        String sql = "INSERT INTO USERS (id, email, username, password, created, updated) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try{
            jdbcTemplate.update(sql,
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getCreated(),
                    user.getUpdated());

            return user;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USERS " +
                "SET email = ?, username = ?, password = ? " +
                "WHERE id = ?";
        try{
            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getId());
            return user;
        }
        catch(DataAccessException e){
            return null;
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
