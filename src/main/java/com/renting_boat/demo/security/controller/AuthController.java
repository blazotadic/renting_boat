package com.renting_boat.demo.security.controller;

import com.renting_boat.demo.exception.CustomSqlException;
import com.renting_boat.demo.security.exception.ValidationException;
import com.renting_boat.demo.security.dto.UserCreateDTO;
import com.renting_boat.demo.security.dto.UserLoginDTO;
import com.renting_boat.demo.security.jwt.JwtTokenProvider;
import com.renting_boat.demo.security.validator.UserCreateValidator;
import com.renting_boat.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserCreateValidator userCreateValidator;
    private final UserService userService;


    @PostMapping(value = "login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userLoginDTO.getUsername(), userLoginDTO.getPassword()
        );
        try
        {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication); // thread local!!!

            String token = jwtTokenProvider.createToken(authentication);
            return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDTO userCreateDTO) throws ValidationException
    {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);

        if (errors.hasErrors()) {
            throw new ValidationException("Register user validation failed!", errors);
        }

        userService.register(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("@customAuth.hasPermissionBasedOnSomething(#authKey)")
    @PostMapping(value = "register-admin")
    public ResponseEntity<Void> registerAdmin(@RequestHeader(value = "Authorization") String authKey, @RequestBody UserCreateDTO userCreateDTO) throws ValidationException, CustomSqlException
    {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);

        if (errors.hasErrors()) {
            throw new ValidationException("Register user validation failed!", errors);
        }

        userService.registerAdmin(userCreateDTO, authKey);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
