package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.RoleDTO;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.mapper.RoleMapper;
import com.renting_boat.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public Role save(RoleDTO roleDTO)
    {
        Role role = roleMapper.toEntity(roleDTO);
        return roleRepository.save(role);
    }

    public void delete(Integer id)throws CustomSqlException {
        if(id>2) {
            roleRepository.deleteById(id);
        }
        else {
            throw new CustomSqlException("You can't delete role with id=1 or id=2");
        }
    }
}
