package com.hermanovich.accountingsystem.dao;

import java.util.List;

public interface GenericDao<T, PK> {

    T getById(PK id);

    void save(T entity);

    void update(T entity);

    void delete(PK id);

    List<T> getAll();
}
