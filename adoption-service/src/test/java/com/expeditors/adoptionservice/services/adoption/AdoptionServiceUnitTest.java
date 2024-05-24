package com.expeditors.adoptionservice.services.adoption;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import com.expeditors.adoptionservice.factory.TestFactory;
import com.expeditors.adoptionservice.service.implementation.AdoptionServiceImpl;
import com.expeditors.adoptionservice.services.BaseServiceUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.containsInRelativeOrder;

@Nested
@ExtendWith(MockitoExtension.class)
class AdoptionServiceUnitTest
    extends BaseServiceUnitTest<Adoption> {

    @Mock
    private BaseDao<Adoption> adoptionBaseDao;
    @InjectMocks
    private AdoptionServiceImpl adoptionService;


    @Override
    @BeforeEach
    public void init() {
        baseDAO = adoptionBaseDao;
        baseService = adoptionService;
        mockEntity = TestFactory.getAdoptionInstance();
    }


    @Test
    public void getAdoptersSortedByDateOfAdoption_RunSuccessful(){

        var adopter1 = new Adopter(1,  "Antonio Nazco", "123-456-789");
        var adopter2 = new Adopter(1,  "Nathaly Nazco", "123-456-789");

        var ad1 = new Adoption(
                1,
                adopter1,
                TestFactory.getPetInstance(),
                LocalDate.now().minusDays(2));

        var ad2 = new Adoption(
                2,
                adopter2,
                TestFactory.getPetInstance(),
                LocalDate.now().minusDays(3));


        doReturn(List.of(ad2,ad1))
                .when(baseDAO).findAll();

        var result = adoptionService
                .getAdoptersSortedByDateOfAdoption();

        assertEquals(2, result.size());
        assertThat(result, containsInRelativeOrder(adopter2, adopter1));
    }


    @Test
    public void getAdoptersSortedByName_RunSuccessful() {

        var adopter1 = new Adopter(1, "Antonio Nazco", "123-456-789");
        var adopter2 = new Adopter(1, "Nathaly Nazco", "123-456-789");

        var ad1 = new Adoption(
                1,
                adopter1,
                TestFactory.getPetInstance(),
                LocalDate.now().minusDays(2));

        var ad2 = new Adoption(
                2,
                adopter2,
                TestFactory.getPetInstance(),
                LocalDate.now().minusDays(3));


        doReturn(List.of(ad2, ad1))
                .when(baseDAO).findAll();

        var result = adoptionService
                .getAdoptersByName("Antonio Nazco");

        assertEquals(1, result.size());
        assertThat(result, contains(adopter1));
    }

}
