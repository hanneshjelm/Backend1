package com.example.hotel.controllers;

import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @BeforeEach
    public void setUp() throws Exception {
        bookingRepo.deleteAll();
        roomRepo.deleteAll();
        roomRepo.save(new Room(1, 18, RoomType.SINGLE));
        roomRepo.save(new Room(2, 18, RoomType.SINGLE));
        roomRepo.save(new Room(3, 25, RoomType.DOUBLE));
        roomRepo.save(new Room(4, 25, RoomType.DOUBLE));
        roomRepo.save(new Room(5, 35, RoomType.DOUBLE));
        roomRepo.save(new Room(6, 35, RoomType.DOUBLE));
    }

    @Test
    public void getAllRoomsTest() throws Exception {
        mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"))
                .andExpect(model().attributeExists("allRooms"))
                .andExpect(model().attribute("allRooms", hasSize(6)))
                .andReturn();
    }
}
