package com.jbq2.simplebankapi.token_management;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpiredTokenService {
    private ExpiredTokenDao expiredTokenDao;

    public String add(String jwt) {
        return expiredTokenDao.add(jwt);
    }

    public boolean exists(String jwt) {
        return expiredTokenDao.exists(jwt);
    }

    public int pop(String jwt) {
        return expiredTokenDao.pop(jwt);
    }
}
