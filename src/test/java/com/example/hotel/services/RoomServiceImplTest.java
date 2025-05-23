package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.impl.RoomServiceImpl;
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

    @Autowired
    private RoomServiceImpl roomServiceImpl;


    Room room1 = new Room(11, 15, RoomType.SINGLE);
    Room room2 = new Room(12, 25, RoomType.DOUBLE);
    Room room3 = new Room(13, 35, RoomType.DOUBLE);

    @Test
    public void roomToRoomDtoTest() throws Exception {
        RoomDto actualDto1 = roomService.roomToRoomDto(room1);
        RoomDto actualDto2 = roomService.roomToRoomDto(room2);

        assertEquals(room1.getRoomNumber(), actualDto1.getRoomNumber());
        assertEquals(room2.getRoomNumber(), actualDto2.getRoomNumber());
        assertEquals(room1.getRoomType().getType(), actualDto1.getRoomTypeString());
    }

    @Test
    public void roomToRoomDetailedDtoTest() throws Exception {
        RoomDetailedDto actualDto1 = roomService.roomToRoomDetailedDto(room2);
        RoomDetailedDto actualDto2 = roomService.roomToRoomDetailedDto(room3);

        assertEquals(room2.getRoomNumber(), actualDto1.getRoomNumber());
        assertEquals(room3.getRoomNumber(), actualDto2.getRoomNumber());
        assertEquals(room2.getSize(), actualDto1.getSize());
    }

    @Test
    public void amountOfExtraBedsTest() throws Exception {
        int actual1 = roomServiceImpl.amountOfExtraBeds(room1);
        int actual2 = roomServiceImpl.amountOfExtraBeds(room2);
        int actual3 = roomServiceImpl.amountOfExtraBeds(room3);

        assertEquals(0, actual1);
        assertEquals(1, actual2);
        assertEquals(2, actual3);
    }

    @Test
    public void totalCapacity() throws Exception {
        int actual1 = roomServiceImpl.totalCapacity(room1);
        int actual2 = roomServiceImpl.totalCapacity(room2);
        int actual3 = roomServiceImpl.totalCapacity(room3);

        assertEquals(1, actual1);
        assertEquals(3, actual2);
        assertEquals(4, actual3);
    }

}
