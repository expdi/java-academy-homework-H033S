package com.expeditors.adoptionservice.domain;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.expeditors.adoptionservice.domain.violations.ConstraintError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

@MappedSuperclass
public abstract class AbstractEntity implements EntityValidable<AbstractEntity> {

    @NotNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    protected int id;

    @Transient
    protected static Validator validator;

    static {
        validator = buildDefaultValidatorFactory().getValidator();
    }


    public AbstractEntity() {

    }

    public AbstractEntity(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Set<ConstraintError> getModelViolations(){

        return validator.validate(this)
                .stream()
                .map(violation -> ConstraintError.builder()
                        .message(violation.getMessage())
                        .fieldName(violation.getPropertyPath().toString())
                        .rejectedValue(Objects.isNull(violation.getInvalidValue())?"null":violation.getInvalidValue().toString())
                        .build())
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    public boolean isModelValid(){
        var modelViolationsFound = getModelViolations().size();
        return modelViolationsFound == 0;
    }
}
