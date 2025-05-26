package com.example.hotel.controllers;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    //private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final CustomerService customerService;

    @RequestMapping("bookings")
    public String getAllBookings(Model model) {
        List<BookingDto> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @RequestMapping("bookings/{id}")
    public String getBooking(@PathVariable Long id, Model model) {
        Optional<BookingDetailedDto> booking = bookingService.findBookingById(id);
        if(booking.isPresent()) {
            model.addAttribute("booking", booking);
            return "bookingDetails";
        }
        model.addAttribute("error", "Booking not found");
        return "/bookings";
    }

    @RequestMapping("bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id, Model model) {
        if (bookingService.deleteBooking(id)){
            return "redirect:/bookings";
        }
        model.addAttribute("error", "Booking with that id not found");
        return "bookings";
    }

    @RequestMapping ("bookings/{id}/update")
    public String updateBooking(@PathVariable Long id, Model model) {
        Optional<BookingDetailedDto> booking = bookingService.findBookingById(id);
        if(booking.isPresent()) {
            model.addAttribute("bookingDetailedDto", booking);
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "updateBooking";
        }
        model.addAttribute("error", "Booking update not possible");
        return "bookings";
    }

    @GetMapping("bookings/create")
    public String createBooking(Model model) {
        model.addAttribute("booking", new BookingDetailedDto());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "createBooking";
    }

    @GetMapping("/searchAvailableRooms")
    public String showSearchForm(Model model) {
        model.addAttribute("booking", new BookingDto());
        return "roomSearch";
    }

    @PostMapping("/searchAvailableRooms")
    public String searchAvailableRooms(
            @ModelAttribute("booking") BookingDto bookingForm,
            Model model
    ) {
        List<RoomDetailedDto> availableRooms = roomService.getAvailableRooms(bookingForm);

        model.addAttribute("rooms", availableRooms);
        return "availableRooms";
    }
}
