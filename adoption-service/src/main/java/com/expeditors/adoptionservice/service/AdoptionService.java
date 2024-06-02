package com.expeditors.adoptionservice.service;

import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.domain.entities.Adoption;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface AdoptionService
        extends BaseService<Adoption> {

    List<Adopter> getAdoptersSortedBy(Comparator<Adoption> comparator);

    List<Adopter> getAdoptersSortedByDateOfAdoption();

    List<Adopter> getAdopterBy(Predicate<Adoption> adopterPredicate);

    List<Adopter> getAdoptersByName(String name);
}
