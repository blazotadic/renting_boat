package com.renting_boat.demo.security.exception;

import com.renting_boat.demo.security.dto.UnauthorizedResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException
    {
        UnauthorizedResponseDTO unauthorizedResponseDTO = new UnauthorizedResponseDTO(
            "Access Denied!", authException.getMessage()
        );

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(unauthorizedResponseDTO));
    }
}
