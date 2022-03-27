package com.renting_boat.demo.dto;

import com.renting_boat.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BoatWithUserDTO {

    private Integer id;
    private String category;
    private Double price;
    private String brand;
    private Date rentingUntil;
    private User user;

}
