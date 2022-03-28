package com.renting_boat.demo.controller;

import com.renting_boat.demo.dto.RoleDTO;
import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "admin/user/all")
    public ResponseEntity<List<UserDTO>> all()
    {
        List<UserDTO> users = userService.all();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "admin/user/with-role/{roleId}")
    public ResponseEntity<List<UserDTO>> allAdmins(@PathVariable Integer roleId)
    {
        List<UserDTO> users = userService.allWithRole(roleId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping(value = "admin/user/add-role/{userId}")
    public ResponseEntity<Void> addRole(@PathVariable Integer userId,
                                                 @RequestHeader(value = "RoleId") Integer roleId)throws CustomSqlException
    {
        userService.addRole(userId, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "admin/user/remove-role/{userId}")
    public ResponseEntity<Void> removeRole(@PathVariable Integer userId,
                                        @RequestHeader(value = "RoleId") Integer roleId)throws CustomSqlException
    {
        userService.removeRole(userId, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "admin/user/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId)throws CustomSqlException
    {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "user/delete-yourself")
    public ResponseEntity<Void> deleteYourself()throws CustomSqlException
    {
        userService.deleteYourself();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "admin/user/remove-rented-boat/{userId}/{boatId}")
    public ResponseEntity<Void> removeRentedBoat(@PathVariable Integer userId,
                                                 @PathVariable Integer boatId) throws CustomSqlException
    {
        userService.removeRentedBoat(userId, boatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "admin/user/with-rented-boats")
    public ResponseEntity<List<UserDTO>> usersWithRentedBoats()
    {
        List<UserDTO> users = userService.usersWithRentedBoats();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
