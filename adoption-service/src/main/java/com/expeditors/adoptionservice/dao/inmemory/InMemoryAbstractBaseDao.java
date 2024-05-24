package com.expeditors.adoptionservice.dao.inmemory;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.AbstractEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryAbstractBaseDao<TEntity extends AbstractEntity>
        implements BaseDao<TEntity> {

    private final Map<Integer,TEntity> entities;
    private static int nextId;


    static {
        nextId = 0;
    }

    public InMemoryAbstractBaseDao() {
        entities = new ConcurrentHashMap<>();
    }

    public TEntity insert(TEntity entity) {
        Objects.requireNonNull(entity);

        nextId++;
        entity.setId(nextId);
        entities.put(entity.getId(), entity);
        return entity;
    }

    public boolean update(TEntity entity) {
        Objects.requireNonNull(entity);

        return entities.putIfAbsent(entity.getId(), entity) != null;
    }

    public boolean delete(int id) {
        return entities.remove(id) != null;
    }
    public TEntity findById(int id) {
        return entities.get(id);
    }

    public List<TEntity> findAll() {
        return entities.values().stream().toList();
    }

}
