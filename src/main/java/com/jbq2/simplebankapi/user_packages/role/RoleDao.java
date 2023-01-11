package com.jbq2.simplebankapi.user_packages.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RoleDao {

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

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        try{
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch(DataAccessException e){
            return null;
        }
    }

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
