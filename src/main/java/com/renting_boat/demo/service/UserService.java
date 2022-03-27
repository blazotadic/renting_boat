package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.entity.User;
import com.renting_boat.demo.mapper.UserMapper;
import com.renting_boat.demo.repository.RoleRepository;
import com.renting_boat.demo.repository.UserRepository;
import com.renting_boat.demo.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public List<UserDTO> all()
    {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::toDTO).collect(Collectors.toList());
    }

    public void register(UserCreateDTO userCreateDTO)
    {
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());

        User user = userMapper.toEntity(userCreateDTO);
        user.setPassword(encodedPassword);
        Optional<Role> role = roleRepository.findById(1);
        if (role.isPresent()) {
            user.addRole(role.get());
        }

        userRepository.save(user);
    }

    public void registerAdmin(UserCreateDTO userCreateDTO)
        {
            String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());

            User user = userMapper.toEntity(userCreateDTO);
            user.setPassword(encodedPassword);
            Optional<Role> role = roleRepository.findById(2);
            if (role.isPresent()) {
                user.addRole(role.get());
            }

            userRepository.save(user);
        }

    public List<UserDTO> allAdmins() {
        {
            List<User> users = userRepository.findAllWithRoleId(2);
            return users
                    .stream()
                    .map(userMapper::toDTO).collect(Collectors.toList());
        }
    }

    public List<UserDTO> allUsers() {
        {
            List<User> users = userRepository.findAllWithRoleId(1);
            return users
                    .stream()
                    .map(userMapper::toDTO).collect(Collectors.toList());
        }
    }
}
