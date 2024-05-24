package com.expeditors.adoptionservice.factory;

import com.expeditors.adoptionservice.domain.entities.*;

import java.time.LocalDate;

public class TestFactory {

    public static Adoption getAdoptionInstance(){
        return new Adoption(
                1,
                getAdopterInstance(),
                getPetInstance(),
                LocalDate.now().plusDays(2));
    }

    public static Adopter getAdopterInstance(){
        return new Adopter(
                1,
                "Antonio Nazco",
                "786-767-1343"
        );
    }

    public static Pet getPetInstance(){
        return  new Pet(
                1,
                PetBreed.SIAMESE,
                PetType.CAT,
                "Suki");
    }
}
