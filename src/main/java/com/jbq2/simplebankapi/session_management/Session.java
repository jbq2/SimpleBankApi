package com.jbq2.simplebankapi.session_management;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Session {
    private String email;
    private String sessionId;
    private Timestamp lastActivity;
}
