package com.example.hotel.services;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.models.Booking;

import java.util.List;

public interface BookingService {

    public BookingDto bookingToBookingDto(Booking b);

    public BookingDetailedDto bookingToBookingDetailedDto(Booking b);

    public List<BookingDto> getAllBookings();

    public Booking findBookingById(Long id);

    public String updateBooking(BookingDetailedDto bookingDetailedDto);

    public String createBooking(BookingDetailedDto bookingDetailedDto);

    public boolean deleteBooking(Long id);

}
