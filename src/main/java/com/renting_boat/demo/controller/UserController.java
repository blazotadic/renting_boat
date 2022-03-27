package com.renting_boat.demo.controller;

import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user/")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "all")
    public ResponseEntity<List<UserDTO>> all()
    {
        List<UserDTO> users = userService.all();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "all-admins")
    public ResponseEntity<List<UserDTO>> allAdmins()
    {
        List<UserDTO> users = userService.allAdmins();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "all-users")
    public ResponseEntity<List<UserDTO>> allUsers()
    {
        List<UserDTO> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
