package com.example.hotel.services.impl;

import com.example.hotel.dtos.BookingDetailedDto;
import org.springframework.context.annotation.Lazy;
import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.RoomService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;

    public RoomServiceImpl(RoomRepository roomRepository, @Lazy BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }
    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::roomToRoomDto).toList();
    }

    @Override
    public Room getRoomById(long id) {
        return roomRepository.findById(id).get();//Behöver checka denna metod om rum inte hittas
    }

    public RoomDto getRoomById2(long id) {
        return roomToRoomDto(roomRepository.findById(id).orElseThrow()); //Behöver checka denna metod om rum inte hittas
    }

    @Override
    public RoomDto roomToRoomDto(Room roomEntity) {
        return RoomDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).roomTypeString(roomEntity.getRoomType().getType()).build();
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room roomEntity) {
        RoomDetailedDto roomDetailedDto = RoomDetailedDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber())
                .size(roomEntity.getSize()).roomTypeString(roomEntity.getRoomType().getType()).bookings(roomEntity.getBookings()
                        .stream().map(booking -> bookingService.bookingToBookingDto(booking)).toList()).build();

        roomDetailedDto.setExtraBeds(amountOfExtraBeds(roomEntity));
        roomDetailedDto.setCapacity(totalCapacity(roomEntity));
        return roomDetailedDto;
    }

    public int amountOfExtraBeds(Room room){
        if (room.getRoomType() == RoomType.SINGLE){
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
        if(room.getRoomType() == RoomType.DOUBLE){
            return RoomType.DOUBLE.getValue() + amountOfExtraBeds(room);
        }

        return room.getRoomType().getValue();
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
