package com.expeditors.adoptionservice.services.adoption;

import com.expeditors.adoptionservice.factory.TestFactory;
import com.expeditors.adoptionservice.service.AdoptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@ActiveProfiles({JDBC, H2})
class AdoptionServiceIntegrationTest__JDBC extends AdoptionServiceIntegrationTest{}
@ActiveProfiles({JDBC_TEMPLATE, H2})
class AdoptionServiceIntegrationTest__JDBC_TEMPLATE extends AdoptionServiceIntegrationTest{}
@ActiveProfiles({JPA, H2})
class AdoptionServiceIntegrationTest__JPA extends AdoptionServiceIntegrationTest{}

@SpringBootTest
@Sql(scripts = {"/sql/h2/0-schema.sql", "/sql/h2/1-test-data.sql"})
public abstract class AdoptionServiceIntegrationTest {

    @Autowired
    AdoptionService adoptionService;

    @Test
    void findAll_RunSuccessfully(){

        var adoptions = adoptionService.getAllEntities();
        Assertions.assertEquals(3, adoptions.size());
    }

    @Test
    void findById_RunSuccessfully_WhenIdIsFound(){

        var adoption = adoptionService.getEntityById(1);
        Assertions.assertEquals(1, adoption.getId());
    }

    @Test
    void findById_RunSuccessfully_WhenIdIsNotFound(){

        var adoption = adoptionService.getEntityById(25);
        Assertions.assertNull(adoption);
    }

    @Test
    void addEntity_RunSuccessfully_WhenCorrectAdoption(){

        var adoption = TestFactory.getAdoptionInstance();
        adoption.setId(0);
        var adoptionResult = adoptionService.addEntity(adoption);

        var elem = adoptionService.getAllEntities();
        elem.forEach(x -> System.out.println("ID: " + x.getId() + " " + x));

        Assertions.assertEquals(4, adoptionResult.getId());
    }

    @Test
    void addEntity_ThrowsIllegalArgumentException_WhenAdoptionIsNull(){

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> adoptionService.addEntity(null));
    }

    @Test
    void updateEntity_ThrowsIllegalArgumentException_WhenAdoptionIsNull(){

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> adoptionService.updateEntity(null));
    }

    @Test
    void updateEntity_RunsSuccessfully_WhenAdoptionIsValid(){

        var date = LocalDate.now().plusDays(2);
        var adoption = TestFactory.getAdoptionInstance();
        adoption.setAdoptionDate(date);

        Assertions.assertTrue(adoptionService.updateEntity(adoption));
        Assertions.assertEquals(date, adoption.getAdoptionDate());
    }

    @Test
    void deleteEntity_RunSuccessfully_WhenIdIsValid(){

        var numberOfAdoptions = adoptionService.getAllEntities().size();
        Assertions.assertTrue(adoptionService.deleteEntity(1));
        Assertions.assertEquals(numberOfAdoptions - 1, adoptionService.getAllEntities().size());
    }

    @Test
    @Sql(scripts = {"/sql/h2/0-schema.sql"})
    void deleteEntity_RunSuccessfully_WhenIdIsNotValid(){

        var numberOfAdoptions = adoptionService.getAllEntities().size();
        Assertions.assertFalse(adoptionService.deleteEntity(1));
        Assertions.assertEquals(numberOfAdoptions, adoptionService.getAllEntities().size());
    }

}
