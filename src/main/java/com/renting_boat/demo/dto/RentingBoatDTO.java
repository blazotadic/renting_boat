package com.renting_boat.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class RentingBoatDTO {
    private Integer boatId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date rentingUntil;
    private String cardNumber;
    private String CardholderName;
    private String MM_YY;
    private Integer cvv;
}
