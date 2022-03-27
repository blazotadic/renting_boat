package com.renting_boat.demo.mapper;

import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.entity.User;
import com.renting_boat.demo.security.dto.UserCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper
{
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    User toEntity(UserCreateDTO userCreateDTO);
}
