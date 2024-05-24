package com.expeditors.adoptionservicehw1213.adopters;

import com.expeditors.adoptionservicehw1213.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdopterRepository extends BaseRepository<Adopter> {
    @Query("SELECT a FROM Adopter a LEFT JOIN FETCH a.adoptedPets")
    List<Adopter> findAll();

    @Query("SELECT a FROM Adopter a LEFT JOIN FETCH a.adoptedPets WHERE a.id = :id")
    Optional<Adopter> findById(@Param("id") Integer id);
}
