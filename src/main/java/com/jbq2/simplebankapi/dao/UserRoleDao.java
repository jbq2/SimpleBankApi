package com.jbq2.simplebankapi.dao;

import com.jbq2.simplebankapi.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.List;

@Component
public class UserRoleDao implements Dao<UserRole> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<UserRole> rowMapper = ((rs, rowNum) -> {
       UserRole userRole = new UserRole();
       userRole.setId(rs.getLong("id"));
       userRole.setUser_id(rs.getLong("user_id"));
       userRole.setRole_id(rs.getLong("role_id"));
       userRole.setCreated(rs.getDate("created").toString());
       userRole.setUpdated(rs.getDate("updated").toString());
       return userRole;
    });

    @Override
    public List<UserRole> findAll() {
        String sql = "SELECT * FROM user_role";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public UserRole findById(Long id) {
        String sql = "SELECT * FROM user_role " +
                "WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public UserRole save(UserRole userRole) {
        String sql = "INSERT INTO user_role (id, user_id, role_id) " +
                "VALUES (?, ?, ?)";
        try{
            jdbcTemplate.update(sql,
                    userRole.getId(),
                    userRole.getUser_id(),
                    userRole.getRole_id());
            return userRole;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public UserRole update(UserRole userRole) {
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

    @Override
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
