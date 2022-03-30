package com.renting_boat.demo.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorDTO {

    private final String field;
    private final String translationKey;
    private final String defaultMessage;

}
