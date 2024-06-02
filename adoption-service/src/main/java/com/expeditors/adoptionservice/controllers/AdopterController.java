package com.expeditors.adoptionservice.controllers;

import com.expeditors.adoptionservice.dto.adopter.AddOrUpdateAdopterDTO;
import com.expeditors.adoptionservice.service.AdopterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/adopter")
public class AdopterController {

    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdopters(){
        return ResponseEntity.ok(
                adopterService.getAllEntities());
    }

    @GetMapping("/{adopterId}")
    public ResponseEntity<?> getAdopterById(
            @PathVariable int adopterId){

        var adopter = adopterService.getEntityById(adopterId);

        if(Objects.isNull(adopter)){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(adopter);
    }

    @PostMapping
    public ResponseEntity<?> addAdopter(
            @Valid @RequestBody AddOrUpdateAdopterDTO adopterRequest){

        var adopterCreated  = adopterService.addEntity(
                        AddOrUpdateAdopterDTO.toAdopter(adopterRequest));

        if(Objects.isNull(adopterCreated)){
            return ResponseEntity
                    .internalServerError()
                    .build();
        }

        return ResponseEntity
                .created(URI.create("/adopter/" + adopterCreated.getId()))
                .body(adopterCreated);
    }

    @PutMapping("/{adopterId}")
    public ResponseEntity<?> updateAdopter(
            @PathVariable int adopterId,
            @Valid @RequestBody AddOrUpdateAdopterDTO adopterRequest){

        var adopterFromDB = adopterService.getEntityById(adopterId);

        if(Objects.isNull(adopterFromDB)){
            return ResponseEntity.notFound().build();
        }

        adopterFromDB.setAdopterName(adopterRequest.getAdopterName());
        adopterFromDB.setPhoneNumber(adopterRequest.getPhoneNumber());
        var isAdopterUpdatedInDb = adopterService.updateEntity(adopterFromDB);

        if(isAdopterUpdatedInDb){
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .internalServerError()
                .build();
    }

    @DeleteMapping("/{adopterId}")
    public ResponseEntity<?> deleteAdopter(
            @PathVariable int adopterId){

        var isAdopterRemovedFromDB = adopterService.deleteEntity(adopterId);

        if(isAdopterRemovedFromDB){
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .internalServerError()
                .build();
    }
}
