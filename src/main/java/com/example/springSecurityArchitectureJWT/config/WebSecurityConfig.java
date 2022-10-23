package com.example.springSecurityArchitectureJWT.config;

import com.example.springSecurityArchitectureJWT.servicesecurity.AppUserDetailsService;
import com.example.springSecurityArchitectureJWT.validators.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS); // Deshabilitamos el sessionid
        http.authorizeRequests().antMatchers("/api/public/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.cors();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filter middleware

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService);
//        auth
//                .inMemoryAuthentication() // Le especificamos que los users and passwords estan en memoria | estos deberian extraerse de una DB externa
//                .withUser("Nicolas").password("{noop}" + "123456").roles("USER")
//                .and()
//                .withUser("Nikola").password("{noop}" + "123456").roles("ADMIN");

    }


}
