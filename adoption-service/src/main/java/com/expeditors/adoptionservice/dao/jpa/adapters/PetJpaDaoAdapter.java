package com.expeditors.adoptionservice.dao.jpa.adapters;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jpa.PetJpaDao;
import com.expeditors.adoptionservice.domain.entities.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JPA;

@Repository
@Profile({JPA })
public class PetJpaDaoAdapter implements BaseDao<Pet> {

    private final PetJpaDao repo;

    public PetJpaDaoAdapter(PetJpaDao repo) {
        this.repo = repo;
    }

    @Override
    public List<Pet> findAll() {
        return repo.findAll();
    }

    @Override
    public Pet findById(int id) {
        return repo
                .findById(id)
                .orElse(null);
    }

    @Override
    public Pet insert(Pet pet) {
        return repo.save(pet);
    }

    @Override
    public boolean update(Pet pet) {
        repo.save(pet);
        return true;
    }

    @Override
    public boolean delete(int id) {

        var petWasFound = repo.existsById(id);
        if(!petWasFound){
            return false;
        }

        repo.deleteById(id);
        return true;
    }
}
