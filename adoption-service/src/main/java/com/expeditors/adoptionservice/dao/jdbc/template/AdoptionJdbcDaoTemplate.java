package com.expeditors.adoptionservice.dao.jdbc.template;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JDBC_TEMPLATE;

@Repository
@Profile(value = {JDBC_TEMPLATE})
public class AdoptionJdbcDaoTemplate
        implements BaseDao<Adoption> {
    @Override
    public List<Adoption> findAll() {
        return null;
    }

    @Override
    public Adoption findById(int id) {
        return null;
    }

    @Override
    public Adoption insert(Adoption adoption) {
        return null;
    }

    @Override
    public boolean update(Adoption adoption) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
