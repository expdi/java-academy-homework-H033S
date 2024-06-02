package com.expeditors.adoptionservice.domain;

import java.util.Set;

import com.expeditors.adoptionservice.domain.violations.ConstraintError;

public interface EntityValidable <T> {

    Set<ConstraintError> getModelViolations();
    boolean isModelValid();
}
