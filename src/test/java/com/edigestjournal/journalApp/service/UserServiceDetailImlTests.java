package com.edigestjournal.journalApp.service;

import com.edigestjournal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.User;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.mockito.Mockito.*;
import com.edigestjournal.journalApp.entity.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;


public class UserServiceDetailImlTests {

   @InjectMocks
    private CustomUserDetailServiceimpl customUserDetailServiceimpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUo(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUsernameNyUsername(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn( User.builder().username("Shreyas").password("Tirthraj").roles(new ArrayList<>()).build());
        UserDetails user = customUserDetailServiceimpl.loadUserByUsername("Shreyas");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("Shreyas",user.getUsername());

    }
}
