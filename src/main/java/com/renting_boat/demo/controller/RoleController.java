package com.renting_boat.demo.controller;

import com.renting_boat.demo.dto.RoleDTO;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin/role/")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("add")
public ResponseEntity<Role> add(@RequestBody RoleDTO roleDTO)throws CustomSqlException
    {
        if (roleDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Role role = roleService.save(roleDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Role> delete(@PathVariable Integer id)throws CustomSqlException
    {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
