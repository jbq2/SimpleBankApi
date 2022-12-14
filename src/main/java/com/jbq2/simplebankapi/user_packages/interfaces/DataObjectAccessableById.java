package com.jbq2.simplebankapi.user_packages.interfaces;

import java.util.List;

public interface DataObjectAccessableById<T extends QueryableById> {
    List<T> findAll();
    T findById(Long id);
    T save(T queryable);
    Boolean delete(Long id);
}
