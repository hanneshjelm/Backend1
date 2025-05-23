package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.RoomRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class RoomServiceImplTest {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private RoomService roomService;


    Room room1 = new Room(11, 15, RoomType.SINGLE);
    Room room2 = new Room(12, 25, RoomType.DOUBLE);
    Room room3 = new Room(13, 35, RoomType.DOUBLE);

    @Test
    public void roomToRoomDetailedDtoTest() throws Exception {
        RoomDetailedDto actualDto1 = roomService.roomToRoomDetailedDto(room1);
        RoomDetailedDto actualDto2 = roomService.roomToRoomDetailedDto(room2);

        assertEquals(actualDto1.getRoomNumber(), room1.getRoomNumber());
        assertEquals(actualDto2.getRoomNumber(), room2.getRoomNumber());
        assertEquals(actualDto1.getSize(), room1.getSize());
    }
}
