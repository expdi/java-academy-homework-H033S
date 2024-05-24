package com.expeditors.adoptionservice.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdopterUnitTest {
    
    @Test
    public void createAdopter_ValidationReturnTrue_WithValidObject(){
        Adopter adopter = new Adopter(
            1,
            "Antonio Nazco", 
            "786-330-3040");

        assertTrue(adopter.isModelValid());
    }

    @Test 
    public void createAdopter_ValidationReturnFalse_WithIncorrectPhoneNumber(){
        Adopter adopter = new Adopter(
            1,
            "Antonio Nazco", 
            "1232-12321");

        assertFalse(adopter.isModelValid());
        assertEquals(1, adopter.getModelViolations().size());

        adopter.getModelViolations().forEach(System.out::println);
    }

    @Test
    public void createAdopter_ValidationReturnTrue_WithCorrectPhoneNumber2(){


        Adopter adopter = new Adopter(
                1,
                "Antonio Nazco",
                "+111 (202) 555-0125");

        assertTrue(adopter.isModelValid());
    }

    @Test 
    public void createAdopter_ValidationReturnFalse_WithIncorrectName(){
        Adopter adopter = new Adopter(
            1,
            "", 
            "786-768-1191");

        assertFalse(adopter.isModelValid());
        assertEquals(1, adopter.getModelViolations().size());
    }

}
