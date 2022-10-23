package com.example.springSecurityArchitectureJWT.controllers;

import com.example.springSecurityArchitectureJWT.models.AuthRequest;
import com.example.springSecurityArchitectureJWT.models.TokenInfo;
import com.example.springSecurityArchitectureJWT.servicesecurity.AppUserDetailsService;
import com.example.springSecurityArchitectureJWT.servicesecurity.JwtUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class GreetController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @GetMapping(value = "/greet")
    public ResponseEntity<?> greet() {
        Map<String, String> greet = new HashMap<>();
        greet.put("colombia", "Que mas pues");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.getPrincipal().toString());
        log.info(auth.getAuthorities().toString());
        log.info(auth.isAuthenticated() + "");
        return new ResponseEntity<>(greet, HttpStatus.OK);
    }


    @GetMapping(value = "/admin")
    public ResponseEntity<?> getMessageAdmin() {
        Map<String, String> greet = new HashMap<>();
        greet.put("colombia", "Que mas pues admin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.getPrincipal().toString());
        log.info(auth.getAuthorities().toString());
        log.info(auth.isAuthenticated() + "");
        log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(greet, HttpStatus.OK);
    }

    @GetMapping(value = "/public")
    public ResponseEntity<?> getMessage() {
        Map<String, String> greet = new HashMap<>();
        greet.put("colombia", "Que mas pues publico");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.getPrincipal().toString());
        log.info(auth.getAuthorities().toString());
        log.info(auth.isAuthenticated() + "");
        return new ResponseEntity<>(greet, HttpStatus.OK);
    }

    @PostMapping(value = "/public/login")
    public ResponseEntity<TokenInfo> auth(
            @RequestBody AuthRequest authRequest
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtilService.generateToken(userDetails);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.isAuthenticated() + "");
        log.info(auth.getAuthorities().toString());
        TokenInfo tokenInfo = new TokenInfo(jwt);
        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
    }

}
