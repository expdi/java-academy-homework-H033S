package com.expeditors.adoptionservice.service.implementation;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.service.AdopterService;
import org.springframework.stereotype.Service;

@Service
public class AdopterServiceImpl
        extends AbstractBaseService<Adopter>
        implements AdopterService {

    public AdopterServiceImpl(BaseDao<Adopter> adopterDAO) {
        super(adopterDAO);
        System.out.println(adopterDAO.getClass().getSimpleName());
    }

}
