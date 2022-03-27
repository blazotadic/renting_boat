package com.renting_boat.demo.error;

import com.renting_boat.demo.exception.CustomSqlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomSqlException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(CustomSqlException e)
    {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
