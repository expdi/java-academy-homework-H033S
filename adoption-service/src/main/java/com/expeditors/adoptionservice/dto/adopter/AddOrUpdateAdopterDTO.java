package com.expeditors.adoptionservice.dto.adopter;

import com.expeditors.adoptionservice.domain.annottations.PhoneNumber;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddOrUpdateAdopterDTO {

    @NotBlank
    private String adopterName;

    @NotBlank
    @PhoneNumber
    private String phoneNumber;


    public static Adopter toAdopter(
            AddOrUpdateAdopterDTO adopterRequest){

        return new Adopter(
                0,
                adopterRequest.getAdopterName(),
                adopterRequest.getPhoneNumber()
        );
    }
}
