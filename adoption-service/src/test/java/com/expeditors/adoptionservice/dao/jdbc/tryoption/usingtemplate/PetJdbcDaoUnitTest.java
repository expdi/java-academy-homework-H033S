package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate;

import com.expeditors.adoptionservice.domain.entities.Pet;
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
public class PetJdbcDaoUnitTest {

    @Autowired
    private PetJdbcDao petDao;

    @Test
    void findAll_RunSuccessfully(){

        List<Pet> pets = petDao.findAll();
        Assertions.assertEquals(4, pets.size(), "We should have only 2 Adoptions is our DB");
        pets.forEach(System.out::println);
    }

    @Test
    void findById_RunSuccessfully_WhenIdIsFound(){

        Pet pet = petDao.findById(1);
        Assertions.assertEquals(1,pet.getId());
        System.out.println(pet);
    }

    @Test
    void deleteId_RunSuccessfully_WhenIdIsFound(){

        boolean deletionResult = petDao.delete(1);
        Assertions.assertTrue(deletionResult);
    }

    @Test
    void deleteId_RunSuccessfully_WhenIdIsNotFound(){

        boolean deletionResult = petDao.delete(25);
        Assertions.assertFalse(deletionResult);
    }

    @Test
    void insertPet_RunSuccessfully(){

        var petToInsert = TestFactory.getPetInstance();
        Pet pet = petDao.insert(petToInsert);
        Assertions.assertEquals(5, pet.getId());
    }


    @Test
    void updatePet_RunSuccessfully(){

        var pet = TestFactory.getPetInstance();
        boolean updateResult = petDao.update(pet);
        Assertions.assertTrue(updateResult);
    }

}
