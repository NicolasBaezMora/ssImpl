package com.example.springSecurityArchitectureJWT.servicesecurity;

import com.example.springSecurityArchitectureJWT.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AppUser> users = new ArrayList<>();
        // simulamos buscarlo en la db
        users.add(AppUser.builder().name("Nicolas").password("12345").role("ADMIN").build());
        users.add(AppUser.builder().name("Nikola").password("12345").role("USER").build());
        AppUser userFound = users.stream().filter(user -> user.getName().equals(username)).collect(Collectors.toList()).get(0);
        if (userFound != null) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(userFound.getPassword()))
                    .roles(userFound.getRole())
                    .build();

        } else {
            log.error(username + " not found");
            throw new UsernameNotFoundException(username + " not found");
        }
    }

}
