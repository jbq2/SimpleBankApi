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
        if(sessionId == null){
            return null;
        }
        Session session = sessionDao.getSessionBySessionId(sessionId);
        if(session == null){
            return null;
        }
        try{
            String email = sessionDao.getSessionBySessionId(sessionId).getEmail();
            touchSession(sessionId);
            return email;
        }
        catch(RuntimeException e){
            return null;
        }
    }

    public void deleteSession(String sessionId){
        sessionDao.deleteSession(sessionId);
    }

    public void touchSession(String sessionId){
        sessionDao.touchSession(sessionId);
    }
}
