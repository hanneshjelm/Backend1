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
        return roomToRoomDetailedDto(roomRepository.findById(id).get()); //Behöver checka denna metod om rum inte hittas
    }

    @Override
    public RoomDto roomToRoomDto(Room roomEntity) {
        return RoomDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).doubleRoom(roomEntity.isDoubleRoom()).build();
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room roomEntity) {
        return RoomDetailedDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber())
                .size(roomEntity.getSize()).doubleRoom(roomEntity.isDoubleRoom()).bookings(roomEntity.getBookings()
                        .stream().map(booking -> bookingService.bookingToBookingDto(booking)).toList()).build();
    }
}
