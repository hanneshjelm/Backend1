package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.impl.RoomServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RoomServiceImplTest {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomServiceImpl roomServiceImpl;

    private Room room1;
    private Room room2;
    private Room room3;

    private RoomDetailedDto actualDto1;
    private RoomDetailedDto actualDto2;
    private RoomDetailedDto actualDto3;

    @BeforeEach
    public void setUp() throws Exception {
        bookingRepo.deleteAll();
        roomRepo.deleteAll();

        room1 = new Room(11, 15, RoomType.SINGLE);
        room2 = new Room(12, 25, RoomType.DOUBLE);
        room3 = new Room(13, 35, RoomType.DOUBLE);

        roomRepo.save(room1);
        roomRepo.save(room2);
        roomRepo.save(room3);

        actualDto1 = roomService.roomToRoomDetailedDto(room1);
        actualDto2 = roomService.roomToRoomDetailedDto(room2);
        actualDto3 = roomService.roomToRoomDetailedDto(room3);
    }

    @Test
    public void getAllRooms() {
        assertEquals(3, roomServiceImpl.getAllRooms().size());
    }

    @Test
    @Transactional
    public void getRoomById() {
        assertEquals(actualDto1, roomServiceImpl.getRoomById(room1.getId()));
        assertEquals(actualDto2, roomServiceImpl.getRoomById(room2.getId()));
        assertEquals(actualDto3.getRoomNumber(), roomServiceImpl.getRoomById(room3.getId()).getRoomNumber());
    }

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
        assertEquals(room2.getRoomNumber(), actualDto2.getRoomNumber());
        assertEquals(room3.getRoomNumber(), actualDto3.getRoomNumber());
        assertEquals(room2.getSize(), actualDto2.getSize());
    }

    @Test
    public void amountOfExtraBedsTest() throws Exception {
        int actualAmount1 = roomServiceImpl.amountOfExtraBeds(room1);
        int actualAmount2 = roomServiceImpl.amountOfExtraBeds(room2);
        int actualAmount3 = roomServiceImpl.amountOfExtraBeds(room3);

        assertEquals(0, actualAmount1);
        assertEquals(1, actualAmount2);
        assertEquals(2, actualAmount3);
    }

    @Test
    public void totalCapacityTest() throws Exception {
        int actualCapacity1 = roomServiceImpl.totalCapacity(room1);
        int actualCapacity2 = roomServiceImpl.totalCapacity(room2);
        int actualCapacity3 = roomServiceImpl.totalCapacity(room3);

        assertEquals(1, actualCapacity1);
        assertEquals(3, actualCapacity2);
        assertEquals(4, actualCapacity3);
    }
}
