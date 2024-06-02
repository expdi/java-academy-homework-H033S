package com.expeditors.adoptionservicehw1213.pets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PetRepositoryUnitTest
        extends PetRepositoryTest{

   @Test
   void findAll_runSuccessfully(){

       var pets = repository.findAll();
       Assertions.assertEquals(6, pets.size());
   }

    @Test
    void findById_ShouldReturnEmpty_WhenIdIsNotFound(){

       var petReturned = repository.findById(100);
       Assertions.assertEquals(Optional.empty(), petReturned);
   }

    @Test
    void findById_ShouldNotBeEmpty_WhenIdIsFound(){

       var petReturned = repository.findById(1);
       Assertions.assertAll(
               () -> Assertions.assertNotEquals(Optional.empty(), petReturned),
               () -> Assertions.assertEquals(1, petReturned.get().getId())
       );
    }

    @Test
    void existsById_ShouldBeFalse_WhenIdIsNotFound(){

       var isPetFound = repository.existsById(100);
       Assertions.assertFalse(isPetFound);
    }

    @Test
    void existsById_ShouldBeTrue_WhenIdIsFound(){
        var isPetFound = repository.existsById(1);
        Assertions.assertTrue(isPetFound);
    }
}
