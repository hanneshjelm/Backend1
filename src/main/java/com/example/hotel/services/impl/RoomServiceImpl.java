package com.example.hotel.services.impl;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingService bookingService;

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::roomToRoomDto).toList();
    }


    public RoomDetailedDto getRoomById(long id) {
        return roomToRoomDetailedDto(roomRepository.findById(id).orElseThrow()); //Behöver checka denna metod om rum inte hittas
    }

    @Override
    public RoomDto roomToRoomDto(Room roomEntity) {
        return RoomDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).doubleRoom(roomEntity.isDoubleRoom()).build();
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room roomEntity) {
        RoomDetailedDto roomDetailedDto = RoomDetailedDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber())
                .size(roomEntity.getSize()).doubleRoom(roomEntity.isDoubleRoom()).bookings(roomEntity.getBookings()
                        .stream().map(booking -> bookingService.bookingToBookingDto(booking)).toList()).build();

        roomDetailedDto.setExtraBeds(amountOfExtraBeds(roomEntity));
        roomDetailedDto.setCapacity(totalCapacity(roomEntity));
        return roomDetailedDto;
    }

    public int amountOfExtraBeds(Room room){
        if (!room.isDoubleRoom()){
            return 0;
        }

        if(room.getSize() < 22){
            return 0;
        } else if(room.getSize() < 30){
            return 1;
        } else {
            return 2;
        }
    }

    public int totalCapacity(Room room){
        if(room.isDoubleRoom()){
            return 2 + amountOfExtraBeds(room);
        }

        return 1;
    }

}
