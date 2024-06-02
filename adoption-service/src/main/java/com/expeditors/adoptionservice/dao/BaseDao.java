package com.expeditors.adoptionservice.dao;


import com.expeditors.adoptionservice.domain.AbstractEntity;

import java.util.List;


public interface BaseDao<TEntity extends AbstractEntity> {
    List<TEntity> findAll();
    TEntity findById(int id);
    TEntity insert(TEntity tEntity);
    boolean update(TEntity tEntity);
    boolean delete(int id);
}
