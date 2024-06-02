package com.expeditors.adoptionservice.validators.domain;

import com.expeditors.adoptionservice.domain.annottations.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private String messageTemplate;
    private Pattern phonePattern;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        messageTemplate = constraintAnnotation.message();
        var strPattern = String.join("|", constraintAnnotation.patterns());

        phonePattern =  Pattern.compile(strPattern);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        var matchingResult = phonePattern.matcher(s);

        if(matchingResult.matches()){
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation();
        return false;
    }
}
