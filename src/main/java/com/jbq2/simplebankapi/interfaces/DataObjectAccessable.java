package com.jbq2.simplebankapi.interfaces;

import java.util.List;

public interface DataObjectAccessable<T extends Queryable> {
    List<T> findAll();
    T findById(Long id);
    T save(T queryable);
    T update(T queryable);
    Boolean delete(Long id);
}
