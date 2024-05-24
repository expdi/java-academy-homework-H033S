package com.expeditors.adoptionservice.dto.pet;

import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.domain.entities.PetBreed;
import com.expeditors.adoptionservice.domain.entities.PetType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddOrUpdatePetDTO {

    @NotNull(message = "{validation.petBreed.null}")
    private PetBreed petBreed;

    @NotNull(message = "{validation.petType.null}")
    private PetType petType;

    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 200, message = "{validation.name.size.too_long}")
    private String petName;

    public static Pet toPet(AddOrUpdatePetDTO adopterRequest) {

        return new Pet(
                0,
                adopterRequest.getPetBreed(),
                adopterRequest.getPetType(),
                adopterRequest.getPetName()
        );
    }
}
