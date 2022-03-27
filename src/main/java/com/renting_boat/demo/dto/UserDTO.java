package com.renting_boat.demo.dto;

import com.renting_boat.demo.entity.Boat;
import com.renting_boat.demo.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
//    private List<Boat> boats = new ArrayList<>();
//    private Set<Role> roles = new HashSet<>();

}
