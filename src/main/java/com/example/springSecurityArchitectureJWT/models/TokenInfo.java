package com.example.springSecurityArchitectureJWT.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class TokenInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String jwtToken;
}
