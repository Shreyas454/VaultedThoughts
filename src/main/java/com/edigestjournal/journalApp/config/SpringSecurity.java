package com.edigestjournal.journalApp.config;

import com.edigestjournal.journalApp.filters.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.edigestjournal.journalApp.service.CustomUserDetailServiceimpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {


    @Autowired
    private CustomUserDetailServiceimpl customUserDetailServiceimpl;

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {// spring autoinjects the arguments/parameters if any overhere when annoted with @Bean

        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/journalV2/**", "/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Code where password is stored with encryption
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailServiceimpl);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
   }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws  Exception{
        return  auth.getAuthenticationManager();
   }


//    //Code where password can be stored without encryption
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailServiceimpl)
//                .passwordEncoder(passwordEncoder());  // Use our no-op encoder
//    }
//
//    // Define a NoOpPasswordEncoder for plain text passwords
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}


//Spring Security by default assumes that we encode the password while storing