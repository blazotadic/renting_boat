package com.renting_boat.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@Getter
@RequiredArgsConstructor
public class CustomSqlException extends Exception {

    private final String message;

}
