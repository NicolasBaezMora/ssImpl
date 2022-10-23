package com.example.springSecurityArchitectureJWT.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthRequest {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
