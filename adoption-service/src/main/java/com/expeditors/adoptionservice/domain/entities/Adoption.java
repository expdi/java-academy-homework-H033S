package com.expeditors.adoptionservice.domain.entities;

import com.expeditors.adoptionservice.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class Adoption extends AbstractEntity {

    @NotNull(message = "{validation.adoption.adopter.null}")
    @ManyToOne
    private Adopter adopter;

    @NotNull(message = "validation.adoption.pet.null")
    @OneToOne
    private Pet pet;

    @Future(message = "{validation.adoption.adoptionDate.past}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate adoptionDate;


    public Adoption(int id, Adopter adopter, Pet pet, LocalDate adoptionDate) {
        this.id = id;
        this.adopter = adopter;
        this.pet = pet;
        this.adoptionDate = adoptionDate;
    }
}
