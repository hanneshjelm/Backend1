package com.example.hotel.controllers;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @RequestMapping("bookings")
    public List<BookingDto> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @RequestMapping("bookings/{id}")
    public BookingDetailedDto getBooking(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @RequestMapping("bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted";
    }

}
