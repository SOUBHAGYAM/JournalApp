package com.Ilearn.journalApp.service;

import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.Repository.UserRepository;
import com.Ilearn.journalApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;


    /*@BeforeEach
    @BeforeEach*/

    @ParameterizedTest
    @CsvSource({
            "ram",
            "Soubhagya",
            "Admin"
    })
    public void findbyusername(String name) {
        assertNotNull(userRepository.findByUsername(name));
      /*  User user = userRepository.findByUsername("Soubhagya");
        assertTrue(!user.getUsername().isEmpty());*/
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b,int expected) {
        assertEquals(expected,a+b);
    }
}
