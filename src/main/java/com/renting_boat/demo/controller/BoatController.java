package com.renting_boat.demo.controller;

import com.renting_boat.demo.dto.BoatDTO;
import com.renting_boat.demo.dto.BoatWithUserDTO;
import com.renting_boat.demo.dto.RentingBoatDTO;
import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.entity.Boat;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.search.BoatSearch;
import com.renting_boat.demo.search.BoatSearchSpecification;
import com.renting_boat.demo.service.BoatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class BoatController {

    private final BoatService boatService;

    @GetMapping(value = "boat/custom-search")
    public ResponseEntity<List<BoatDTO>> customSearch(BoatSearch boatSearch)
    {
        BoatSearchSpecification boatSearchSpecification = new BoatSearchSpecification(boatSearch);
        List<BoatDTO> boats = boatService.customSearch(boatSearchSpecification);

        return new ResponseEntity<>(boats, HttpStatus.OK);
    }

    @PostMapping(value = "admin/boat/add")
    public ResponseEntity<Void> store(@RequestBody BoatDTO boatDTO)
    {
        if (boatDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boatService.save(boatDTO);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201!
    }

    @DeleteMapping("admin/boat/delete/{id}")
    public ResponseEntity<Role> delete(@PathVariable Integer id)throws CustomSqlException
    {
        boatService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "admin/boat/rented")
    public ResponseEntity<List<Boat>> rentedBoat()
    {
        List<Boat> BoatWithUser = boatService.rentedBoat();
        return new ResponseEntity<>(BoatWithUser, HttpStatus.OK);
    }


    @PostMapping(value = "boat/renting")
    public ResponseEntity<Void> rentingBoat(@RequestBody RentingBoatDTO rentingBoatDTO) throws CustomSqlException {
        boatService.rentingBoat(rentingBoatDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "boat/getAllCategories")
    public ResponseEntity<Set<String>> getAllCategory()
    {
        Set<String> AllCategory = boatService.getAllCategories();
        return new ResponseEntity<>(AllCategory, HttpStatus.OK);
    }

    @GetMapping(value = "boat/getAllBrands")
    public ResponseEntity<Set<String>> getAllBrands()
    {
        Set<String> AllBrands = boatService.getAllBrands();
        return new ResponseEntity<>(AllBrands, HttpStatus.OK);
    }
}
