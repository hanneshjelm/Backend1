package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RoomServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceImplTest.class);
    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {
        bookingRepo.deleteAll();
        roomRepo.deleteAll();
        roomRepo.save(new Room(1, 11, 15, RoomType.SINGLE, Collections.emptyList()));
        roomRepo.save(new Room(2, 13, 35, RoomType.DOUBLE, Collections.emptyList()));
    }

    Room room1 = new Room(11, 15, RoomType.SINGLE);
    Room room2 = new Room(12, 25, RoomType.DOUBLE);
    Room room3 = new Room(13, 35, RoomType.DOUBLE);

    RoomDetailedDto detailedDto1 = new RoomDetailedDto(1, 11, 15, "single room", Collections.emptyList(), 0, 1);
    RoomDetailedDto detailedDto2 = new RoomDetailedDto(2, 13, 35, "double room", Collections.emptyList(), 2, 4);

    @Test//Behövs verkligen denna? Testmetod i RoomController
    public void getAllRooms() {
        assertEquals(2, roomServiceImpl.getAllRooms().size());
    }

    //@Test
    //public void getRoomById() {
      //  assertEquals(detailedDto1, roomServiceImpl.getRoomById(1));
        //assertEquals(detailedDto2, roomServiceImpl.getRoomById(2));
    //}

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
    public void totalCapacityTest() throws Exception {
        int actual1 = roomServiceImpl.totalCapacity(room1);
        int actual2 = roomServiceImpl.totalCapacity(room2);
        int actual3 = roomServiceImpl.totalCapacity(room3);

        assertEquals(1, actual1);
        assertEquals(3, actual2);
        assertEquals(4, actual3);
    }
}
