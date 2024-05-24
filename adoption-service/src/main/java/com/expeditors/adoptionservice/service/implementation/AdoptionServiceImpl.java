
package com.expeditors.adoptionservice.service.implementation;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import com.expeditors.adoptionservice.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AdoptionServiceImpl
        extends AbstractBaseService<Adoption>
        implements AdoptionService  {

    @Autowired
    public AdoptionServiceImpl(BaseDao<Adoption> adoptionDAO) {
        super(adoptionDAO);
        System.out.println(adoptionDAO.getClass().getSimpleName());

    }

    @Override
    public List<Adopter> getAdoptersSortedBy(Comparator<Adoption> comparator) {
        return entityDAO.findAll().stream()
                .sorted(comparator)
                .map(Adoption::getAdopter)
                .toList();
    }

    @Override
    public List<Adopter> getAdoptersSortedByDateOfAdoption() {
        return getAdoptersSortedBy(Comparator.comparing(Adoption::getAdoptionDate));
    }

    @Override
    public List<Adopter> getAdopterBy(Predicate<Adoption> adopterPredicate) {
        return entityDAO.findAll().stream()
                .filter(adopterPredicate)
                .map(Adoption::getAdopter)
                .toList();
    }

    @Override
    public List<Adopter> getAdoptersByName(String name) {
        return getAdopterBy(adoption -> adoption
                .getAdopter()
                .getAdopterName()
                .equalsIgnoreCase(name));
    }

}