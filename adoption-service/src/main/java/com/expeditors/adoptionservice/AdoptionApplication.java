package com.expeditors.adoptionservice;

import com.expeditors.adoptionservice.domain.entities.*;
import com.expeditors.adoptionservice.service.AdopterService;
import com.expeditors.adoptionservice.service.PetService;
import com.expeditors.adoptionservice.service.implementation.AdoptionServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@SpringBootApplication
public class AdoptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptionApplication.class, args);
	}
}

@Component
@Profile(value = {"!"+H2})
class  RunApp implements CommandLineRunner{

	private final AdoptionServiceImpl adoptionService;
	private final AdopterService adopterService;
	private final PetService petService;


	public RunApp(
			AdoptionServiceImpl adoptionService,
			AdopterService adopterService,
			PetService petService) {

		this.adoptionService = adoptionService;
        this.adopterService = adopterService;
        this.petService = petService;
    }

	@Override
	public void run(String... args)   {
		 var adopter1 = new Adopter(0, "Antonio Nazco", "123-123-4567");
		 var adopter2 = new Adopter(0, "Nathaly Nazco", "123-123-4567");

		 var pet1 = new Pet(0, PetBreed.POODLE, PetType.DOG,  "Suki" );
		 var pet2 = new Pet(0, PetBreed.SIAMESE, PetType.CAT,  "Bing" );
		 var pet3 = new Pet(0, PetBreed.SIAMESE, PetType.CAT,  "Bang" );

		 var ld1 = LocalDate.now().plusDays(3);
		 var ld2 = LocalDate.now().plusDays(4);
		 var ld3 = LocalDate.now().plusDays(5);

		 var adoption1 = new Adoption(0, adopter1, pet1, ld1);
		 var adoption2 = new Adoption(0, adopter2, pet2, ld2);
		 var adoption3 = new Adoption(0, adopter2, pet3, ld3);

		 petService.addEntity(pet1);
		 petService.addEntity(pet2);
		 petService.addEntity(pet3);

		 adopterService.addEntity(adopter1);
		 adopterService.addEntity(adopter2);

		 adoptionService.addEntity( adoption1);
		 adoptionService.addEntity( adoption2);
		 adoptionService.addEntity( adoption3);

		 System.out.println(adoptionService.getClass().getSimpleName());
		 var adoptions  = adoptionService.getAllEntities();
		 System.out.println();
		 adoptions.forEach(System.out::println);
		 System.out.println();
	}
}