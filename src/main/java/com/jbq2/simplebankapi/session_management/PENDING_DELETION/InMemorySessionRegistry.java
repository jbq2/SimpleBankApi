package com.jbq2.simplebankapi.session_management.PENDING_DELETION;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemorySessionRegistry {
    private static final Map<String, String> SESSIONS = new HashMap<>();

    public String registerSession(String email){
        if(email == null){
            throw new RuntimeException("Email must be provided.");
        }

        final String sessionId = generateSessionId();
        SESSIONS.put(sessionId, email);
        return sessionId;
    }

    public String getEmailForSession(final String sessionId){
        return SESSIONS.get(sessionId);
    }

    private String generateSessionId(){
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
    }
}
