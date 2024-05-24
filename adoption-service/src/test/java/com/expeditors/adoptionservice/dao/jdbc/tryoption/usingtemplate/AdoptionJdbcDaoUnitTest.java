package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate;

import com.expeditors.adoptionservice.domain.entities.Adoption;
import com.expeditors.adoptionservice.factory.TestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@SpringBootTest
@ActiveProfiles({JDBC, H2})
@Sql(scripts = {"/sql/h2/0-schema.sql", "/sql/h2/1-test-data.sql"})
public class AdoptionJdbcDaoUnitTest {

    @Autowired
    private AdoptionJdbcDao adoptionDao;

    @Test
    void findAll_RunSuccessfully(){

        List<Adoption> adoptions = adoptionDao.findAll();
        Assertions.assertEquals(3, adoptions.size(), "We should have only 2 Adoptions is our DB");
        adoptions.forEach(System.out::println);
    }

    @Test
    void findById_RunSuccessfully_WhenIdIsFound(){

        Adoption adoption = adoptionDao.findById(1);
        Assertions.assertEquals(1,adoption.getId());
        Assertions.assertEquals(1,adoption.getAdopter().getId());
        Assertions.assertEquals(1,adoption.getPet().getId());
        System.out.println(adoption);
    }

    @Test
    void deleteId_RunSuccessfully_WhenIdIsFound(){

        boolean deletionResult = adoptionDao.delete(1);
        Assertions.assertTrue(deletionResult);
    }

    @Test
    void deleteId_RunSuccessfully_WhenIdIsNotFound(){

        boolean deletionResult = adoptionDao.delete(25);
        Assertions.assertFalse(deletionResult);
    }

    @Test
    void insertAdoption_RunSuccessfully(){

        var adoptionToInsert = TestFactory.getAdoptionInstance();
        Adoption adoption = adoptionDao.insert(adoptionToInsert);
        Assertions.assertEquals(4, adoption.getId());
    }


    @Test
    void updateAdoption_RunSuccessfully(){

        var adoption = TestFactory.getAdoptionInstance();
        boolean updateResult = adoptionDao.update(adoption);
        Assertions.assertTrue(updateResult);
    }

}

