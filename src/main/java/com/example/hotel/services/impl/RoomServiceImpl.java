package com.example.hotel.services.impl;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.mappers.RoomMapper;
import org.springframework.context.annotation.Lazy;
import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Booking;
import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.RoomService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomDto roomToRoomDto(Room room) {
        return roomMapper.roomToRoomDto(room);
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room room) {
        return roomMapper.roomToRoomDetailedDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::roomToRoomDto).toList();
    }

    @Override
    public Room getRoomById(long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public RoomDto getRoomById2(long id) {
        return roomToRoomDto(roomRepository.findById(id).orElseThrow());
    }

    public List<RoomDetailedDto> getAvailableRooms (BookingDetailedDto b) {

        LocalDate checkIn = b.getCheckInDate();
        LocalDate checkOut = b.getCheckOutDate();
        int guestRequirement= b.getGuests();

        if (checkIn == null || checkOut == null || checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Invalid check-in/check-out dates");
        }

        if (guestRequirement <= 0) {
            throw new IllegalArgumentException("Invalid guest requirement");
        }

        return roomRepository.findAvailableRooms(checkIn, checkOut)
                .stream()
                .map(this::roomToRoomDetailedDto)
                .filter(room -> room.getCapacity() >= guestRequirement)
                .toList();
    }

}
