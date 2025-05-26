package com.example.hotel;

import com.example.hotel.controllers.RoomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;



import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MainTest {

    @Autowired
    private RoomController roomController;

    @Test
    void contextLoads() {
        assertThat(roomController).isNotNull(); //Behövdes den här?
    }
}
