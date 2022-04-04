package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.RoleDTO;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.mapper.RoleMapper;
import com.renting_boat.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public Role save(RoleDTO roleDTO)throws CustomSqlException {
        if (roleDTO.getName() != null && roleDTO.getDescription() != null) {
            Role role = roleMapper.toEntity(roleDTO);
            return roleRepository.save(role);
        }
        else {
            throw new CustomSqlException("You must have name and description");
        }
    }

    public void delete(Integer id)throws CustomSqlException {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()) {
            Role newRole = role.get();
            if (id > 2) {
                if(newRole.getUsers().isEmpty()) {
                    roleRepository.deleteById(id);
                }
                else { throw new CustomSqlException("That role is assigned to the user"); }
            }
            else { throw new CustomSqlException("You can't delete role with id=1 or id=2"); }
        }
        else { throw new CustomSqlException("This role doesn't exist"); }
    }
}
