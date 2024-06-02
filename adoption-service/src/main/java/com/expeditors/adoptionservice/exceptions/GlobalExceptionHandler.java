package com.expeditors.adoptionservice.exceptions;

import com.expeditors.adoptionservice.domain.violations.ConstraintError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception){

            ProblemDetail validationProblemDetail = 
                ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST, 
                    "Validation Error");

            List<ConstraintError> errors = exception.getFieldErrors()
            .stream()
            .map(violation -> ConstraintError
                    .builder()
                    .message(violation.getDefaultMessage())
                    .fieldName(violation.getField())
                    .rejectedValue(
                            Objects.isNull(violation.getRejectedValue()) ?
                                    "null" :
                                    violation.getRejectedValue().toString()
                    )
                    .build())
            .toList();

            validationProblemDetail.setProperty("errors", errors);
            return validationProblemDetail;
        }


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalExceptions(
            Exception exception){

        ProblemDetail validationProblemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Validation Error");

        validationProblemDetail.setProperty(
                "ExceptionError",
                exception.getMessage());

        return validationProblemDetail;
    }
}
