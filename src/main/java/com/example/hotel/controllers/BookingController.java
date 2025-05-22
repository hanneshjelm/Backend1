package com.example.hotel.controllers;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;

    @RequestMapping("bookings")
    public List<BookingDetailedDto> getAllBookings() {
        return bookingService.getAllBookings();
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
