package com.jbq2.simplebankapi.session_management;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionDao sessionDao;

    public String registerSession(String email){
        return sessionDao.registerSession(email);
    }

    public String getEmailOfSession(String sessionId){
        String email = sessionDao.getSessionBySessionId(sessionId).getEmail();
        touchSession(sessionId);
        return email;
    }

    public void deleteSession(String sessionId){
        sessionDao.deleteSession(sessionId);
    }

    private void touchSession(String sessionId){
        sessionDao.touchSession(sessionId);
    }
}
