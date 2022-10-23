package com.example.springSecurityArchitectureJWT.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    private String name;
    private String password;
    private String role;
}
