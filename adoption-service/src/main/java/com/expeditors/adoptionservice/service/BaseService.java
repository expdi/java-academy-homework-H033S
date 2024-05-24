package com.expeditors.adoptionservice.service;

import com.expeditors.adoptionservice.domain.AbstractEntity;

import java.util.List;

public interface BaseService<TEntity extends AbstractEntity> {
    List<TEntity> getAllEntities();

    TEntity addEntity(TEntity tEntity);

    boolean updateEntity(TEntity tEntity);

    boolean deleteEntity(int id);

    TEntity getEntityById(int id);
}
