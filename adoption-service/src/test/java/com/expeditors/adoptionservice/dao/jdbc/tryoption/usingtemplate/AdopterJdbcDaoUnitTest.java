package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate;

import com.expeditors.adoptionservice.domain.entities.Adopter;
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
public class AdopterJdbcDaoUnitTest {

    @Autowired
    private AdopterJdbcDao adopterDao;

    @Test
    void findAll_RunSuccessfully(){

        List<Adopter> adopters = adopterDao.findAll();
        Assertions.assertEquals(2, adopters.size(), "We should have only 2 Adoptions is our DB");
        adopters.forEach(System.out::println);
    }

    @Test
    void findById_RunSuccessfully_WhenIdIsFound(){

        Adopter adopter = adopterDao.findById(1);
        Assertions.assertEquals(1,adopter.getId());
        System.out.println(adopter);
    }

    @Test
    void deleteId_RunSuccessfully_WhenIdIsFound(){

        boolean deletionResult = adopterDao.delete(1);
        Assertions.assertTrue(deletionResult);
    }

    @Test
    void delete_RunSuccessfully_WhenIdIsNotFound(){

        boolean deletionResult = adopterDao.delete(25);
        Assertions.assertFalse(deletionResult);
    }

    @Test
    void insertPet_RunSuccessfully(){

        var adopterToInsert = TestFactory.getAdopterInstance();
        Adopter adopter = adopterDao.insert(adopterToInsert);
        Assertions.assertEquals(3, adopter.getId());
    }

    @Test
    void updatePet_RunSuccessfully(){

        var adopter = TestFactory.getAdopterInstance();
        boolean updateResult = adopterDao.update(adopter);
        Assertions.assertTrue(updateResult);
    }
}
