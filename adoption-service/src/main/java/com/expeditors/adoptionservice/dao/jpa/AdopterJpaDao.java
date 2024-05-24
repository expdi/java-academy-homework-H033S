package com.expeditors.adoptionservice.dao.jpa;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JPA;

@Repository
@Transactional
@Profile({JPA })
public class AdopterJpaDao implements BaseDao<Adopter> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Adopter> findAll() {
        TypedQuery<Adopter> adopters = em.createQuery("SELECT ADT FROM Adopter ADT", Adopter.class);
        return adopters.getResultList();
    }

    @Override
    public Adopter findById(int id) {
        return em.find(Adopter.class, id);
    }

    @Override
    public Adopter insert(Adopter adopter) {
        em.persist(adopter);
        return adopter;
    }

    @Override
    public boolean update(Adopter adopter) {
        em.merge(adopter);
        return true;
    }

    @Override
    public boolean delete(int id) {
        Adopter adopter = findById(id);

        if(adopter == null){
            return false;
        }
        em.remove(adopter);
        return true;
    }
}
