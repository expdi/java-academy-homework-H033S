package com.expeditors.adoptionservice.services.pet;

import com.expeditors.adoptionservice.factory.TestFactory;
import com.expeditors.adoptionservice.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@ActiveProfiles({JDBC, H2})
class PetServiceIntegrationTest__JDBC extends PetServiceIntegrationTest{}
@ActiveProfiles({JDBC_TEMPLATE, H2})
class PetServiceIntegrationTest__JDBC_TEMPLATE extends PetServiceIntegrationTest{}
@ActiveProfiles({JPA, H2})
class PetServiceIntegrationTest__JPA extends PetServiceIntegrationTest{}


    @SpringBootTest
    @Sql(scripts = {"/sql/h2/0-schema.sql", "/sql/h2/1-test-data.sql"})
    public abstract class PetServiceIntegrationTest {

        @Autowired
        PetService petService;

        @Test
        void findAll_RunSuccessfully(){

            var pets = petService.getAllEntities();
            Assertions.assertEquals(4, pets.size());
        }

        @Test
        void findById_RunSuccessfully_WhenIdIsFound(){

            var pet = petService.getEntityById(1);
            Assertions.assertEquals(1, pet.getId());
        }

        @Test
        void findById_RunSuccessfully_WhenIdIsNotFound(){

            var pet = petService.getEntityById(25);
            Assertions.assertNull(pet);
        }

        @Test
        void addEntity_RunSuccessfully_WhenCorrectPet(){

            var pet = TestFactory.getPetInstance();
            pet.setId(0);
            var petResult = petService.addEntity(pet);

            Assertions.assertEquals(5, petResult.getId());
        }

        @Test
        void addEntity_ThrowsIllegalArgumentException_WhenPetIsNull(){

            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> petService.addEntity(null));
        }

        @Test
    void updateEntity_ThrowsIllegalArgumentException_WhenpetIsNull(){

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> petService.updateEntity(null));
    }

    @Test
    void updateEntity_RunsSuccessfully_WhenPetIsValid(){

        var date = LocalDate.now().plusDays(2);
        var pet = TestFactory.getPetInstance();
        pet.setPetName("Test");

        Assertions.assertTrue(petService.updateEntity(pet));
        Assertions.assertEquals("Test", pet.getPetName());
    }

    @Test
    void deleteEntity_RunSuccessfully_WhenIdIsValid(){

        var numberOfPets = petService.getAllEntities().size();
        Assertions.assertTrue(petService.deleteEntity(1));
        Assertions.assertEquals(numberOfPets - 1, petService.getAllEntities().size());
    }

    @Test
    @Sql(scripts = {"/sql/h2/0-schema.sql"})
    void deleteEntity_RunSuccessfully_WhenIdIsNotValid(){

        var numberOfPets = petService.getAllEntities().size();
        Assertions.assertFalse(petService.deleteEntity(1));
        Assertions.assertEquals(numberOfPets, petService.getAllEntities().size());
    }

}
