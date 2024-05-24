package com.expeditors.adoptionservicehw1213.adopters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdopterRepositoryTest {

    @Autowired
    AdopterRepository repository;

    @BeforeAll
    void beforeEach(){
        repository.saveAll(generateAdopters());
    }

    private static List<Adopter> generateAdopters(){

        List<Adopter> adopters = new ArrayList<>();
        adopters.add(new Adopter("Antonio", "786-123-123"));
        adopters.add(new Adopter("Nathy", "786-123-123"));
        adopters.add(new Adopter("Laura", "786-123-123"));
        adopters.add(new Adopter("Dora", "786-123-123"));
        return adopters;
    }

//    @AfterAll
//    void afterAll(){
//        repository.deleteAll();
//    }
}
