package com.expeditors.adoptionservicehw1213.pets;

import com.expeditors.adoptionservicehw1213.pets.types.Cat;
import com.expeditors.adoptionservicehw1213.pets.types.Dog;
import com.expeditors.adoptionservicehw1213.pets.types.Elephant;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetRepositoryTest {

    @Autowired
    PetRepository repository;

    @BeforeAll
    public void beforeAll(){
        repository.saveAll(generatePets());
    }

   private static List<Pet> generatePets(){

       List<Pet> pets = new ArrayList<>();
       pets.add(new Dog("Suki", PetBreed.FRENCH_BULLDOG));
       pets.add(new Cat("Lazy", PetBreed.PERSIAN));
       pets.add(new Cat("Loky", PetBreed.MAIN_COON));
       pets.add(new Dog("Mota", PetBreed.BEAGLE));
       pets.add(new Elephant("Kiki", PetBreed.AFRICAN));
       pets.add(new Dog("Miki", PetBreed.POODLE));
       return pets;
   }

   @AfterAll
   void afterAll(){
        repository.deleteAll();
   }
}
