package com.renting_boat.demo.service;

import com.renting_boat.demo.dto.UserDTO;
import com.renting_boat.demo.entity.Boat;
import com.renting_boat.demo.entity.Role;
import com.renting_boat.demo.entity.User;
import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.mapper.UserMapper;
import com.renting_boat.demo.repository.BoatRepository;
import com.renting_boat.demo.repository.RoleRepository;
import com.renting_boat.demo.repository.UserRepository;
import com.renting_boat.demo.security.dto.UserCreateDTO;
import com.renting_boat.demo.security.treds.LocalPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BoatRepository boatRepository;
    private final LocalPrincipal localPrincipal;


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

    public List<UserDTO> allWithRole(Integer roleId) {
        {
            List<User> users = userRepository.findAllWithRoleId(roleId);
            return users
                    .stream()
                    .map(userMapper::toDTO).collect(Collectors.toList());
        }
    }


    public void addRole(Integer userId, Integer roleId) throws CustomSqlException
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Optional<Role> role = roleRepository.findById(roleId);
            if(role.isPresent()){
                User newUser = user.get();
                newUser.addRole(role.get());
                userRepository.save(newUser);
            }
            else{ throw new CustomSqlException("Role doesn't exist");}
        }
        else{ throw new CustomSqlException("User doesn't exist");}


    }

    public void removeRole(Integer userId, Integer roleId) throws CustomSqlException
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Optional<Role> role = roleRepository.findById(roleId);
            if(role.isPresent()){
                User newUser = user.get();
                newUser.removeRole(role.get());
                userRepository.save(newUser);
            }
            else{ throw new CustomSqlException("Role doesn't exist");}
        }
        else{ throw new CustomSqlException("User doesn't exist");}
    }

    public void deleteUser(Integer userId) throws CustomSqlException
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User newUser = user.get();
            if(newUser.getBoats().isEmpty()){
                newUser.removeAllRole();
                userRepository.delete(newUser);
            }
            else{ throw new CustomSqlException("User doesn't delete, because he didn't return the renting boats");}
        }
        else{ throw new CustomSqlException("User doesn't exist");}

    }


    public List<UserDTO> usersWithRentedBoats() {
        List<User> users = userRepository.findAllWithRentedBoats();
        return users
                .stream()
                .map(userMapper::toDTO).collect(Collectors.toList());
    }

    public void deleteYourself() throws CustomSqlException
    {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(localPrincipal.getPrincipal()));
        if(user.isPresent()){
            User newUser = user.get();
            if(newUser.getBoats().isEmpty()){
                newUser.removeAllRole();
                userRepository.deleteById(newUser.getId());
            }
            else{ throw new CustomSqlException("User doesn't delete, because he didn't return the renting boats");}
        }
        else{ throw new CustomSqlException("User doesn't exist");}
    }

    public void removeRentedBoat(Integer userId, Integer boatId) throws CustomSqlException
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Optional<Boat> boat = boatRepository.findById(boatId);
            if(boat.isPresent()){
                Boat newBoat = boat.get();
                newBoat.removeUserAndRentingUntil();
                boatRepository.save(newBoat);
            }
            else{ throw new CustomSqlException("Boat doesn't exist");}
        }
        else{ throw new CustomSqlException("User doesn't exist");}
    }
}
