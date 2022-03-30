package com.renting_boat.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDTOWithErrors {

    private String message;
    private List<FieldErrorDTO> errors;

}
