package com.jbq2.simplebankapi.dao;

import com.jbq2.simplebankapi.pojo.Role;
import com.jbq2.simplebankapi.pojo.interfaces.Queryable;

import java.util.List;

public interface Dao <T extends Queryable> {
    List<T> findAll();
    T findById(Long id);
    T save(T queryable);
    T update(T queryable);
    Boolean delete(Long id);
}
