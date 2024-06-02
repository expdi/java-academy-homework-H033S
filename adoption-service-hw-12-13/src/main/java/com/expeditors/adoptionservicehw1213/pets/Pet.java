package com.expeditors.adoptionservicehw1213.pets;

import com.expeditors.adoptionservicehw1213.common.AbstractEntity;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pet extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private PetBreed breed;


    public Pet() {
    }

    public Pet(String name, PetBreed breed) {
        this.name = name;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetBreed getBreed() {
        return breed;
    }

    public void setBreed(PetBreed breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", breed=" + breed +
                ", id=" + id +
                '}';
    }
}
