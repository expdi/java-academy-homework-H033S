package com.expeditors.adoptionservicehw1213.adopters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AdopterRepositoryUnitTest
        extends AdopterRepositoryTest{

    @Test
    void findAll_runSuccessfully(){

        var adopters = repository.findAll();
        Assertions.assertEquals(4, adopters.size());
    }


    @Test
    void findById_ShouldBeValid_WhenIdIsFound(){

        var adopterOpt = repository.findById(1);
        Assertions.assertNotEquals(Optional.empty(), adopterOpt);
        Assertions.assertEquals(1, adopterOpt.get().getId());
    }
}
