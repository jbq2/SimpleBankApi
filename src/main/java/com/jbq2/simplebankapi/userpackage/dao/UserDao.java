package com.jbq2.simplebankapi.userpackage.dao;

import com.jbq2.simplebankapi.interfaces.DataObjectAccessable;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
/* data access object for User */
public class UserDao implements DataObjectAccessable<User> {

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
        String sql = "INSERT INTO USERS (id, email, password, created, updated) " +
                "VALUES (?, ?, ?, ?, ?)";

        try{
            jdbcTemplate.update(sql,
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getCreated(),
                    user.getUpdated());
            user = findByEmail(user.getEmail());
            return user;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public User update(User user) {
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
