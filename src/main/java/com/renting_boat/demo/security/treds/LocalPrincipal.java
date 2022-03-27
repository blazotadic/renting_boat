package com.renting_boat.demo.security.treds;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class LocalPrincipal {
    private String principal;
}
