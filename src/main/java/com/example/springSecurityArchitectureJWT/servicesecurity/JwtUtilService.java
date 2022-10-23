package com.example.springSecurityArchitectureJWT.servicesecurity;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtilService {

    private static final String JWT_SECRET_KEY = "TOP5ECRET";

    private static final Long JWT_TOKEN_VALIDITY = 1000 * 60 * 30L;

    public Claims getJwtBody(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Invalid token");
        }
    }

    public String getUsernameFromJWT(String token) {
        return getJwtBody(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(
                "roles",
                userDetails.getAuthorities()
        );
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage(), e);
            throw new UnsupportedJwtException("Token format invalid");
        } catch (MalformedJwtException e) {
            log.error(e.getMessage(), e);
            throw new MalformedJwtException("Token build invalid");
        } catch (SignatureException e) {
            log.error(e.getMessage(), e);
            throw new SignatureException("Token signature invalid");
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new ExpiredJwtException(null, getJwtBody(token), "Token Expired");
        }
    }

}
