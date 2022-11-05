package com.jbq2.simplebankapi.dao;

import com.jbq2.simplebankapi.pojo.User;

import java.util.Collection;

public interface UserDao {
    Collection<User> findAll();
    User findById(Long id);
    User save(User user);
    User update(User user);
    User delete(Long id);
}
