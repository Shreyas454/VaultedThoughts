package com.edigestjournal.journalApp.service;

import com.edigestjournal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    public UserRepository userRepository;


    @Test
    public  void testfindByUserName(){

        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUsername("Shreyas"));
        assertTrue(7<8);
    }

    @Disabled //when entire class is run this test will be ignored
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "7,8,15"
    })
    public void test(int a , int b , int expected){
        assertEquals(expected, a+b );
    }
}
