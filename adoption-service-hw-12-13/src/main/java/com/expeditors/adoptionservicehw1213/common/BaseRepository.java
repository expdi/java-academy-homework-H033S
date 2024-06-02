package com.expeditors.adoptionservicehw1213.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<TEntity extends AbstractEntity>
        extends Repository<TEntity, Integer> {

    List<TEntity> findAll();
    boolean existsById(Integer id);
    Optional<TEntity> findById(Integer id);
    TEntity save(TEntity entity);
    void saveAll(Iterable<TEntity> entities);
    TEntity deleteById(Integer id);
    void deleteAll();

}
