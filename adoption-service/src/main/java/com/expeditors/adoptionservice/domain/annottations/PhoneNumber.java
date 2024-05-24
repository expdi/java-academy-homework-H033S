package com.expeditors.adoptionservice.domain.annottations;

import java.lang.annotation.*;

import com.expeditors.adoptionservice.validators.domain.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ 
    ElementType.FIELD, 
    ElementType.PARAMETER }
    )
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Documented
public @interface PhoneNumber {

    String message() default "{validation.adopter.phone-number.incorrect}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

    String [] patterns() default {
            "^\\d{3}[-.\\s]\\d{3}[-.\\s]\\d{4}$",
            "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
    };
}
