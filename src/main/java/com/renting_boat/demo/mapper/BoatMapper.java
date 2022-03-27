package com.renting_boat.demo.mapper;


import com.renting_boat.demo.dto.BoatDTO;
import com.renting_boat.demo.entity.Boat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoatMapper
{
    Boat mapToEntity(BoatDTO boatDTO);
    BoatDTO mapToDTO(Boat boat);
}