package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.BoatDTO;
import com.renting_boat.demo.dto.BoatWithUserDTO;
import com.renting_boat.demo.dto.RentingBoatDTO;
import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.entity.Boat;
import com.renting_boat.demo.entity.User;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.mapper.BoatMapper;
import com.renting_boat.demo.repository.BoatRepository;
import com.renting_boat.demo.repository.UserRepository;
import com.renting_boat.demo.search.BoatSearchSpecification;
import com.renting_boat.demo.security.treds.LocalPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoatService {

    private final BoatRepository boatRepository;
    private final UserRepository userRepository;
    private final BoatMapper boatMapper;
    private final LocalPrincipal localPrincipal;

    public List<BoatDTO> customSearch(BoatSearchSpecification boatSearchSpecification)
    {
      List<Boat> resultSet = boatRepository.findAll(boatSearchSpecification);
        List<BoatDTO> boats= resultSet.stream()
            .map(boatMapper::mapToDTO)
            .collect(Collectors.toList());
        return boats;
    }

    public void save(BoatDTO boatDTO) {
        Boat boat = boatMapper.mapToEntity(boatDTO);
        boatRepository.save(boat);
    }

    public void delete(Integer id) throws CustomSqlException {
        Optional<Boat> boat = boatRepository.findById(id);
        if(boat.isPresent()) {
            boatRepository.deleteById(id);
        }
        else {throw new CustomSqlException("Boat doesn't exist");}
    }

    public List<Boat> rentedBoat()
    {
        return boatRepository.findAllRentedBoats();
    }


    //@Transactional
    public void rentingBoat(RentingBoatDTO rentingBoatDTO) throws CustomSqlException
    {
        if(rentingBoatDTO.getBoatId() != null && rentingBoatDTO.getRentingUntil() !=null) {
            Optional<Boat> boat = boatRepository.findById(rentingBoatDTO.getBoatId());
            if (boat.isPresent()) {
                Boat newBoat = boat.get();
                if (newBoat.getRentingUntil() == null && newBoat.getUser() == null) {
                    newBoat.setRentingUntil(rentingBoatDTO.getRentingUntil());
                    Optional<User> user = Optional.ofNullable(userRepository.findByUsername(localPrincipal.getPrincipal()));
                    if (user.isPresent()) {
                        User newUser = user.get();
                        newUser.addBoat(newBoat);
                        newBoat.setUser(newUser);

                        //transakcija sa bankom, ako je prodje sve se vraca na staro jer je transakciona metoda u pitanju
                    } else {
                        throw new CustomSqlException("Error, try later");
                    }
                } else {
                    throw new CustomSqlException("Boat is busy");
                }

            } else {
                throw new CustomSqlException("Boat doesn't exist");
            }
        }
        else{ throw new CustomSqlException("boatId or rentingUntil is empty");}
    }

}
