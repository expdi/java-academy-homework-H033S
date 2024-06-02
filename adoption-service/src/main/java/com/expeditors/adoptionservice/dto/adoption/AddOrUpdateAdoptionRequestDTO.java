package com.expeditors.adoptionservice.dto.adoption;

import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddOrUpdateAdoptionRequestDTO {

    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate adoptionDate;

    @NotNull
    private int adopterId;

    @NotNull
    private int petId;

    public static Adoption createAdoption(
            LocalDate adoptionDate,
            Adopter associatedAdopter,
            Pet associatedPet) {

        return new Adoption(0, associatedAdopter, associatedPet, adoptionDate);
    }
}
