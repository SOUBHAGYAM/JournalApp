package com.Ilearn.journalApp.service;

import com.Ilearn.journalApp.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
@Disabled
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername(){
   /*     when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .username("Soubhagya")
                        .password("test123")
                        .roles(new ArrayList<>())
                        .build());*/

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Assertions.assertNotNull(userDetails);
    }
}
