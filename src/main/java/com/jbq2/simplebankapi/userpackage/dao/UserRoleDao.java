package com.jbq2.simplebankapi.userpackage.dao;

import com.jbq2.simplebankapi.interfaces.DataObjectAccessable;
import com.jbq2.simplebankapi.userpackage.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
/* data access object for UserRole */
public class UserRoleDao implements DataObjectAccessable<UserRole> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<UserRole> userRoleRowMapper = ((rs, rowNum) -> {
       UserRole userRole = new UserRole();
       userRole.setId(rs.getLong("id"));
       userRole.setUser_id(rs.getLong("user_id"));
       userRole.setRole_id(rs.getLong("role_id"));
       userRole.setCreated(rs.getDate("created").toString());
       userRole.setUpdated(rs.getDate("updated").toString());
       return userRole;
    });

    RowMapper<String> stringRowMapper = ((rs, rowNum) -> {
        return rs.getString("name");
    });

    @Override
    public List<UserRole> findAll() {
        String sql = "SELECT * FROM user_role";
        try{
            return jdbcTemplate.query(sql, userRoleRowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

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

    @Override
    public UserRole findById(Long id) {
        String sql = "SELECT * FROM user_role " +
                "WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, userRoleRowMapper, id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
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
