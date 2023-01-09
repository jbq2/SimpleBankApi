package com.jbq2.simplebankapi.token_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ExpiredTokenDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Token> tokenRowMapper = ((rs, rowNum) -> {
        Token token = new Token();
        token.setId(rs.getLong("id"));
        token.setToken(rs.getString("token"));
        return token;
    });

    public String add(String jwt) {
        try {
            String sql = "INSERT INTO BLOCKED_TOKENS (token) " +
                    "VALUES (?)";
            jdbcTemplate.update(sql, jwt);
            return jwt;
        }
        catch(DataAccessException e) {
            return null;
        }
    }

    public boolean exists(String jwt) {
        try {
            String sql = "SELECT * FROM BLOCKED_TOKENS WHERE token = ?";
            jdbcTemplate.queryForObject(sql, tokenRowMapper, jwt);
            return true;
        }
        catch(DataAccessException e) {
            return false;
        }
    }

    public int pop(String jwt) {
        try {
            String sql = "DELETE FROM BLOCKED_TOKENS WHERE token = ?";
            return jdbcTemplate.update(sql, jwt);
        }
        catch(DataAccessException e) {
            return -1;
        }
    }
}
