package com.expeditors.adoptionservice.dao.inmemory.implementation;

import com.expeditors.adoptionservice.dao.inmemory.InMemoryAbstractBaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.IN_MEMORY;

@Repository
@Profile(IN_MEMORY)
public class InMemoryAdopterDao
        extends InMemoryAbstractBaseDao<Adopter> {


}
