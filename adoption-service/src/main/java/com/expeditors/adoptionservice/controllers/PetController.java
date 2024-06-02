package com.expeditors.adoptionservice.controllers;

import com.expeditors.adoptionservice.dto.pet.AddOrUpdatePetDTO;
import com.expeditors.adoptionservice.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;


@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdopters() {
        return ResponseEntity.ok(
                petService.getAllEntities());
    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetById(
            @PathVariable int petId) {

        var pet = petService.getEntityById(petId);

        if (Objects.isNull(pet)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(pet);
    }

    @PostMapping
    public ResponseEntity<?> addPet(
            @Valid @RequestBody AddOrUpdatePetDTO petRequest) {

        var petCreated = petService.addEntity(
                AddOrUpdatePetDTO.toPet(petRequest));

        if (Objects.isNull(petCreated)) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }

        return ResponseEntity
                .created(URI.create("/pet/" + petCreated.getId()))
                .body(petCreated);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<?> updatePet(
            @PathVariable int petId,
            @Valid @RequestBody AddOrUpdatePetDTO petRequest) {

        var petFromDB = petService.getEntityById(petId);

        if (Objects.isNull(petFromDB)) {
            return ResponseEntity.notFound().build();
        }

        petFromDB.setPetName(petRequest.getPetName());
        petFromDB.setPetBreed(petRequest.getPetBreed());
        petFromDB.setPetType(petRequest.getPetType());

        var isPetUpdatedInDb = petService.updateEntity(petFromDB);

        if (isPetUpdatedInDb) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .internalServerError()
                .build();
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(
            @PathVariable int petId) {

        var isPetRemovedFromDB = petService.deleteEntity(petId);

        if (isPetRemovedFromDB) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .internalServerError()
                .build();
    }
}
