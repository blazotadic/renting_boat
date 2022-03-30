package com.renting_boat.demo.error;

import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.security.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomSqlException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(CustomSqlException e)
    {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTOWithErrors> handleValidationException(ValidationException e)
    {
        List<FieldErrorDTO> customFieldErrors = new ArrayList<>();

        Errors errors = e.getErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors)
        {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage()
            );
            customFieldErrors.add(fieldErrorDTO);
        }

        ErrorDTOWithErrors errorDTOWithErrors = new ErrorDTOWithErrors(e.getMessage(), customFieldErrors);
        return new ResponseEntity<>(errorDTOWithErrors, HttpStatus.BAD_REQUEST);
    }
}
