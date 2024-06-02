package com.expeditors.adoptionservice.service.implementation;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.service.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl
        extends AbstractBaseService<Pet>
        implements PetService {

    public PetServiceImpl(BaseDao<Pet> petDAO) {
        super(petDAO);
        System.out.println(petDAO.getClass().getSimpleName());
    }

}
