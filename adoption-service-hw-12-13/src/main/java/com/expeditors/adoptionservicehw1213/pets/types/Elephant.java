package com.expeditors.adoptionservicehw1213.pets.types;

import com.expeditors.adoptionservicehw1213.pets.Pet;
import com.expeditors.adoptionservicehw1213.pets.PetBreed;
import jakarta.persistence.Entity;

@Entity
public class Elephant extends Pet {

    public Elephant(String name, PetBreed breed) {
        super(name, breed);
    }

    public Elephant() {

    }
}
