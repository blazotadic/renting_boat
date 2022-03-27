package com.renting_boat.demo.security.jwt;

import com.renting_boat.demo.entity.User;
import com.renting_boat.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if (user != null)   //da li taj korisnik postoji u bazi
        {
            List<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()); //Sve role korisnika u listu ROLE_ADMIN, ROLE_DEVELOPER

            return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                grantedAuthorities
            );
        }
        else {
            throw new UsernameNotFoundException("User with given username not found!");
        }
    }
}
