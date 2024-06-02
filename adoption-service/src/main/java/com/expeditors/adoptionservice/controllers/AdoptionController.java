package com.expeditors.adoptionservice.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;

import com.expeditors.adoptionservice.dto.adoption.AddOrUpdateAdoptionRequestDTO;
import com.expeditors.adoptionservice.service.AdopterService;
import com.expeditors.adoptionservice.service.AdoptionService;
import com.expeditors.adoptionservice.service.PetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.expeditors.adoptionservice.service.implementation.AdoptionServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adoption")
public class AdoptionController {
    
    private final AdoptionService adoptionService;
    private final PetService petService;
    private final AdopterService adopterService;

    public AdoptionController(
            AdoptionServiceImpl adoptionService,
            PetService petService,
            AdopterService adopterService) {

        this.adoptionService = adoptionService;
        this.petService = petService;
        this.adopterService = adopterService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> listAdoptions(){

        return ResponseEntity
                .ok()
                .body(adoptionService.getAllEntities());
    }

    @GetMapping("/all/{adoptionDate}")
    public ResponseEntity<?> listAdoptionsByDate(
            @PathVariable("adoptionDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate adoptionDate){

        return ResponseEntity
                .ok()
                .body(adoptionService
                        .getAllEntities()
                        .stream()
                        .filter(adoption -> adoption.getAdoptionDate().equals(adoptionDate))
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdoption(
            @PathVariable("id") int adoptionId){

        var adoption = adoptionService.getEntityById(adoptionId);

        if(Objects.isNull(adoption)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(adoption);
    }

    @PostMapping
    public ResponseEntity<?> addAdoption(
         @Valid @RequestBody AddOrUpdateAdoptionRequestDTO adoptionRequest){

        var associatedPet = petService.getEntityById(adoptionRequest.getPetId());
        var associatedAdopter = adopterService.getEntityById(adoptionRequest.getAdopterId());
        var adoption = AddOrUpdateAdoptionRequestDTO.createAdoption(
                adoptionRequest.getAdoptionDate(),
                associatedAdopter,
                associatedPet);

        if(adoption.isModelValid()){
            var adoptionCreatedResult = adoptionService.addEntity(adoption);
            return ResponseEntity
                    .created(URI.create("/adoption/" + adoptionCreatedResult.getId()))
                    .body(adoptionCreatedResult);
        }

        return ResponseEntity.badRequest()
                .body(adoption.getModelViolations());
    }

    @PutMapping("/{adoptionId}")
    public ResponseEntity<?> updateAdoption(
            @PathVariable("adoptionId") int adoptionId,
            @Valid @RequestBody AddOrUpdateAdoptionRequestDTO adoptionRequest){

        var associatedPet = petService.getEntityById(adoptionRequest.getPetId());
        var associatedAdopter = adopterService.getEntityById(adoptionRequest.getAdopterId());
        var adoption = AddOrUpdateAdoptionRequestDTO.createAdoption(
                adoptionRequest.getAdoptionDate(),
                associatedAdopter,
                associatedPet);
        adoption.setId(adoptionId);

        if(adoption.isModelValid()){
            var isAdoptionCreatedSuccessfully = adoptionService.updateEntity(adoption);

            if(isAdoptionCreatedSuccessfully){
                return ResponseEntity
                        .noContent()
                        .build();
            }

            return ResponseEntity
                    .internalServerError()
                    .build();
        }

        return ResponseEntity.badRequest()
                .body(adoption.getModelViolations());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdoption(
            @PathVariable("id") int adoptionId){

        var adoption = adoptionService.getEntityById(adoptionId);

        if(Objects.isNull(adoption)){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if(adoptionService.deleteEntity(adoptionId)){
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .internalServerError()
                .build();
    }
}
