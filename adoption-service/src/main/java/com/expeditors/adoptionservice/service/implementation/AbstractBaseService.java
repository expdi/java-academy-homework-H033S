package com.expeditors.adoptionservice.service.implementation;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.AbstractEntity;
import com.expeditors.adoptionservice.service.BaseService;

import java.util.List;
import java.util.Objects;

public abstract class AbstractBaseService<TEntity extends AbstractEntity>
        implements BaseService<TEntity> {

    protected final BaseDao<TEntity> entityDAO;

    protected AbstractBaseService(BaseDao<TEntity> entityDAO) {
        this.entityDAO = entityDAO;
    }

    @Override
    public List<TEntity> getAllEntities(){
        return entityDAO.findAll();
    }

    @Override
    public TEntity addEntity(TEntity tEntity){
        if(Objects.isNull(tEntity)){
            throw new IllegalArgumentException(
                    "Adoption cannot be null");
        }

        return entityDAO.insert(tEntity);
    }

    @Override
    public boolean updateEntity(TEntity tEntity){
        if(Objects.isNull(tEntity)){
            throw new IllegalArgumentException(
                    "Adoption cannot be null");
        }

        return entityDAO.update(tEntity);
    }

    @Override
    public boolean deleteEntity(int id){
        return entityDAO.delete(id);
    }

    @Override
    public TEntity getEntityById(int id){
        return entityDAO.findById(id);
    }

}
