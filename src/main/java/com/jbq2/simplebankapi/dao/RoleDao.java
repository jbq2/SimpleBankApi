package com.jbq2.simplebankapi.dao;

import com.jbq2.simplebankapi.dao.Dao;
import com.jbq2.simplebankapi.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleDao implements Dao<Role> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Role> rowMapper = ((rs, rowNum) -> {
       Role role = new Role();
       role.setId(rs.getLong("id"));
       role.setName(rs.getString("name"));
       role.setCreated(rs.getDate("created").toString());
       role.setUpdated(rs.getDate("updated").toString());
       return role;
    });

    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Role findById(Long id) {
        String sql = "SELECT * FROM roles " +
                "WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Role save(Role role) {
        String sql = "INSERT INTO roles (id, name, created, updated) " +
                "VALUES (?, ?, ?, ?)";
        try{
            jdbcTemplate.update(sql,
                    role.getId(),
                    role.getName(),
                    role.getCreated(),
                    role.getUpdated());
            return role;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Role update(Role role) {
        String sql = "UPDATE roles " +
                "SET name = ?, created = ?, updated = ? " +
                "WHERE id = ?";
        try{
            jdbcTemplate.update(sql,
                    role.getName(),
                    role.getCreated(),
                    role.getUpdated(),
                    role.getId());
            return role;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
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
