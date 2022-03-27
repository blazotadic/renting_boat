package com.renting_boat.demo.security.jwt;

import com.renting_boat.demo.security.treds.LocalPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final LocalPrincipal localPrincipal;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        try
        {
            String jwt = resolveToken(request); //da li je proslijedjen token
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt))    //da li je validan
            {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication); //lokalni tredovi
                //log.info((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                localPrincipal.setPrincipal((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
            filterChain.doFilter(request, response);    //nastavak lanca filtera
        }
        catch (ExpiredJwtException | SignatureException
                | MalformedJwtException | UnsupportedJwtException
                | IllegalArgumentException e)
        {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addIntHeader("Access-Control-Max-Age", 10);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


    private String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
