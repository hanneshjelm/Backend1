package com.example.hotel.controllers;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.models.Booking;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @RequestMapping("bookings")
    public List<BookingDto> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @RequestMapping("bookings/{id}")
    public Booking getBooking(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @RequestMapping("bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted";
    }

    @PutMapping ("bookings/{id}/update")
    public String updateBooking(@PathVariable Long id, @RequestBody BookingDetailedDto bookingDetailedDto) {
        bookingDetailedDto.setId(id);
        return bookingService.updateBooking(bookingDetailedDto);
    }

    @RequestMapping("bookings/create")
    public String createBooking(@RequestBody BookingDetailedDto bookingDetailedDto) {
        return bookingService.createBooking(bookingDetailedDto);
    }



    @GetMapping("/searchAvaliableRooms")
    public String showSearchForm(Model model) {
        model.addAttribute("booking", new BookingDto());
        return "roomSearch";
    }

    @PostMapping("/searchAvaliableRooms")
    public String searchAvailableRooms(
            @ModelAttribute("booking") BookingDto bookingForm,
            Model model
    ) {
        List<RoomDetailedDto> availableRooms = roomService.getAvailableRooms(bookingForm);

        model.addAttribute("rooms", availableRooms);
        return "avaliableRooms";
    }
}
