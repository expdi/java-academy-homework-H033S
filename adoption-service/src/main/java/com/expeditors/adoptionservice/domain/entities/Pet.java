package com.expeditors.adoptionservice.domain.entities;

import com.expeditors.adoptionservice.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pet extends AbstractEntity {

    @NotNull(message = "{validation.petBreed.null}")
    @Column(
            name = "breed",
            nullable = false
    )
    @Enumerated(value = EnumType.STRING)
    private PetBreed petBreed;    

    @NotNull(message = "{validation.petType.null}")
    @Column(
            name = "type",
            nullable = false
    )
    @Enumerated(value = EnumType.STRING)
    private PetType petType;

    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 200, message = "{validation.name.size.too_long}")
    @Column(
            name = "name",
            nullable = false
    )
    private String petName;

    public Pet(int id, PetBreed breed, PetType type, String name) {
        this.id = id;
        this.petBreed = breed;
        this.petType = type;
        this.petName = name;
    }
}
