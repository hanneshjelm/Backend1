package com.example.hotel.services.impl;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Booking;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public List<BookingDetailedDto> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::bookingToBookingDetailedDto).toList();
    }

    @Override
    public BookingDto bookingToBookingDto(Booking b) {
        return BookingDto.builder().id(b.getId()).guests(b.getGuests()).checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public BookingDetailedDto bookingToBookingDetailedDto(Booking b) {
        return BookingDetailedDto.builder().id(b.getId()).customer(new CustomerDto(b.getCustomer().getId(), b.getCustomer().getName()))
                .room(new RoomDto(b.getRoom().getId(), b.getRoom().getRoomNumber(), b.getRoom().getRoomType())).guests(b.getGuests()).checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate()).build();
    }

}
