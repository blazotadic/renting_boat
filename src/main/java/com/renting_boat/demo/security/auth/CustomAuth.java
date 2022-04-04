package com.renting_boat.demo.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // customAuth
@RequiredArgsConstructor
public class CustomAuth {


    @Value("${authKey}")
    private String authKey;

    public boolean hasPermissionBasedOnSomething(String providedKey)
    {
        return authKey.equals(providedKey);
    }

}
