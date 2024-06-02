package com.expeditors.adoptionservice.domain.violations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintError {

    private String fieldName;
    private String message;
    private String rejectedValue;
}
