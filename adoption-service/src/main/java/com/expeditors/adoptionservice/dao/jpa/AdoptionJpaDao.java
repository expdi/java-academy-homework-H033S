package com.expeditors.adoptionservice.dao.jpa;

import com.expeditors.adoptionservice.domain.entities.Adoption;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JPA;

@Repository
@Profile({JPA })
public interface AdoptionJpaDao extends JpaRepository<Adoption, Integer> {

    @Query("SELECT a FROM Adoption a JOIN FETCH a.adopter JOIN FETCH a.pet")
    List<Adoption> findAllAdoptionWithAdopterAndPet();

    @Query("SELECT a FROM Adoption a JOIN FETCH a.adopter JOIN FETCH a.pet WHERE a.id = :id")
    Optional<Adoption> findById(int id);

}

