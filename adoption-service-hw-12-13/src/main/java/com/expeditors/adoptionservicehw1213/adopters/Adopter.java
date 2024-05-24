package com.expeditors.adoptionservicehw1213.adopters;

import com.expeditors.adoptionservicehw1213.common.AbstractEntity;
import com.expeditors.adoptionservicehw1213.pets.Pet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Adopter extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(
            name = "phone_number",
            nullable = false)
    private String phoneNumber;

    @OneToMany
    @Column(name = "adopters_pet")
    private Set<Pet> adoptedPets = new HashSet<>();

    public Adopter() {
    }

    public Adopter(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Adopter(int id, String name, String phoneNumber) {
        super(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    public void setAdoptedPets(Set<Pet> adoptedPets) {
        this.adoptedPets = adoptedPets;
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "id=" + getId() + " " +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

