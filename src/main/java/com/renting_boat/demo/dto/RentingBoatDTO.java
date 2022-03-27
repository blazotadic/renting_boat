package com.renting_boat.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class RentingBoatDTO {
    private Integer boatId;
    private Date rentingUntil;
    private Integer cardNumber;
    private String CardholderName;
    private String MM_YY;
    private Integer cvv;
}
