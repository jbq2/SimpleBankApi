package com.jbq2.simplebankapi.session_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Repository
public class SessionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Session> sessionRowMapper = ((rs, rowNum) -> {
        Session session = new Session();
        session.setEmail(rs.getString("email"));
        session.setSessionId(rs.getString("session_id"));
        session.setLastActivity(rs.getTimestamp("last_activity"));
        return session;
    });

    public String registerSession(String email){
        String sql = "INSERT INTO sessions (email, session_id) " +
                "VALUES (?, ?)";

        String sessionId = generateSessionId();
        jdbcTemplate.update(sql, email, sessionId);

        return sessionId;
    }

    public void touchSession(String sessionId){
        String sql = "UPDATE sessions " +
                "SET last_activity = CURRENT_TIMESTAMP " +
                "WHERE session_id = ?";

        jdbcTemplate.update(sql, sessionId);
    }

    public void deleteSession(String sessionId){
        String sql = "DELETE FROM sessions " +
                "WHERE session_id = ?";

        jdbcTemplate.update(sql, sessionId);
    }

    public Session getSessionBySessionId(String sessionId) {
        String sql = "SELECT * FROM sessions " +
                "WHERE session_id = ?";

        return jdbcTemplate.queryForObject(sql, sessionRowMapper, sessionId);
    }

    private String generateSessionId(){
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
    }
}
