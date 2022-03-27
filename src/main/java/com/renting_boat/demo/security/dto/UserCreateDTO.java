package com.renting_boat.demo.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
