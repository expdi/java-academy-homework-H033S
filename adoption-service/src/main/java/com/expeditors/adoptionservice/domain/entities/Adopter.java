package com.expeditors.adoptionservice.domain.entities;

import com.expeditors.adoptionservice.domain.AbstractEntity;
import com.expeditors.adoptionservice.domain.annottations.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Adopter extends AbstractEntity {

    @Size(min = 2, message = "{validation.adopter.name.size.too_short}")
    @Size(max = 200, message = "{validation.adopter.name.size.too_long}")
    @Column(
            name = "name",
            nullable = false
    )
    private String adopterName;

    @PhoneNumber(message = "{validation.adopter.phone-number.incorrect}")
    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;

    public Adopter(int id, String adopterName, String phoneNumber) {
        this.id = id;
        this.adopterName = adopterName;
        this.phoneNumber = phoneNumber;
    }
}

