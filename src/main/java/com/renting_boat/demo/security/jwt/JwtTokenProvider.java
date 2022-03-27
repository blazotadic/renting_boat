package com.renting_boat.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validityInMinutes}")
    private long tokenValidityInMinutes;


    public String createToken(Authentication authentication)
    {
        String authorities = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));      //sve role pretvara u string

        long now = new Date().getTime();
        Date validity = new Date(now + tokenValidityInMinutes * 60_000);

        return Jwts.builder()
            .setSubject(authentication.getName())           //uzmem username
            .claim(AUTHORITIES_KEY, authorities)            //podesavamo role pod ovim kljucem
            .signWith(SignatureAlgorithm.HS512, secretKey)  //kazemo koji algoritam koristimo za kreiranje tokena i proslijedimo tajni kljuc
            .setExpiration(validity)                        //kazemo koliko token traje
            .compact();                                     //formiramo string koji predstavlja token
    }

    public Authentication getAuthentication(String token)   //od tokena autentification object
    {
        Claims claims = Jwts.parser()                       //uzimamo body
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        String principal = claims.getSubject();             //uzimamo korisnika

        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());  //kolekcija od rola

        //vracamo username password AuthenticationToken, password nikad ne prosledjujemo u token
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken)  //prosledjujemo autorizacioni token
    {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);   //kazemo koji je tajni kljuc
        return true;                                                        //ako nema exception vracam true
    }
}
