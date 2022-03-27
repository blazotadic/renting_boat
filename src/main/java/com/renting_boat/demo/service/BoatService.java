package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.BoatDTO;
import com.renting_boat.demo.entity.Boat;
import com.renting_boat.demo.mapper.BoatMapper;
import com.renting_boat.demo.repository.BoatRepository;
import com.renting_boat.demo.search.BoatSearchSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoatService {

    private final BoatRepository boatRepository;
    private final BoatMapper boatMapper;

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

    public void delete(Integer id) {
        boatRepository.deleteById(id);
    }
}
