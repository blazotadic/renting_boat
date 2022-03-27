package com.renting_boat.demo.mapper;

import com.renting_boat.demo.dto.RoleDTO;
import com.renting_boat.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper
{
    RoleDTO toDTO(Role role);
    Role toEntity(RoleDTO roleDTO);
}
