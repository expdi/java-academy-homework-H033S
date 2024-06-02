package com.expeditors.adoptionservice.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class PetUnitTest {
    
    @Test
    public void createPet_ValidationReturnTrue_WithValidObject(){
        Pet pet = new Pet(
            1,
            PetBreed.SIAMESE,
            PetType.CAT,
            "Suki");

        assertTrue(pet.isModelValid());
    }

    @Test
    public void createPet_ValidationReturnFalse_WithIncorrectObject(){
        Pet pet = new Pet(
            1,
            null, 
            null, 
            "");

        assertFalse(pet.isModelValid());
        assertEquals(3, pet.getModelViolations().size());
    }
}
