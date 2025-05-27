package com.example.hotel.controllers;

import com.example.hotel.dtos.*;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.models.Booking;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
import com.example.hotel.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final CustomerService customerService;
    private static final Logger log = Logger.getLogger(BookingController.class.getName());


    @RequestMapping("bookings")
    public String getAllBookings(Model model) {
        List<BookingDto> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @RequestMapping("bookings/{id}")
    public String getBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findBookingById(id);
        if (booking != null) {
            BookingDetailedDto bookingDetailedDto = bookingService.bookingToBookingDetailedDto(booking);
            model.addAttribute("bookingDetailedDto", bookingDetailedDto);
            return "bookingDetails";
        }
        return "redirect:/bookings";
    }

    @RequestMapping("bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/bookings";
    }

    @RequestMapping("bookings/{id}/deleteFromCustomer/{customerId}")
    public String deleteBooking2(@PathVariable Long id, @PathVariable Long customerId) {
        bookingRepository.deleteById(id);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping ("bookings/{id}/update")
    public String updateBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findBookingById(id);
        if (booking != null) {
            BookingDetailedDto bookingDetailedDto = bookingService.bookingToBookingDetailedDto(booking);
            model.addAttribute("booking", bookingDetailedDto);
            //model.addAttribute("customers", customerService.getAllCustomers());
            //model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("editBookingId", id);
            return "roomSearch";
        }
        return "redirect:/bookings";
    }

    @PostMapping("bookings/{id}/update")
    public String updateBooking(@PathVariable Long id, @ModelAttribute BookingDetailedDto bookingDetailedDto, Model model) {

        if (bookingDetailedDto.getCustomer() == null ||
                bookingDetailedDto.getRoom() == null ||
                bookingDetailedDto.getCheckInDate() == null) {

            model.addAttribute("error", "Was not able to update booking details");
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "roomSearch";
        }

        bookingService.updateBooking(bookingDetailedDto);

        return "redirect:/bookings/" + id;
    }

    @GetMapping("bookings/create")
    public String createBooking(@ModelAttribute("booking") BookingDetailedDto bookingForm, Model model) {
        bookingService.createBooking(bookingForm);
        return "bookingResult";
    }

    @GetMapping("/searchAvailableRooms")
    public String showSearchForm(Model model) {
        model.addAttribute("booking", new BookingDetailedDto());
        return "roomSearch";
    }

    @PostMapping("/searchAvailableRooms")
    public String searchAvailableRooms(
            @ModelAttribute("booking") BookingDetailedDto bookingForm,@RequestParam(required = false) Long editBookingId,
            Model model
    ) {
        List<RoomDetailedDto> availableRooms = roomService.getAvailableRooms(bookingForm);
        model.addAttribute("booking", bookingForm);
        model.addAttribute("rooms", availableRooms);

        if(editBookingId != null) {
            model.addAttribute("editBookingId", editBookingId);
        }

        return "availableRooms";
    }

    @PostMapping("registerBooking")
    public String registerBooking(
            @Valid @ModelAttribute("booking") BookingDetailedDto booking,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Validation failed. Please check your inputs.");
            return "customerForBooking";
        }
        CustomerDto dto = customerService.findByPhoneNumber(booking.getCustomer().getPhoneNumber());
        if (dto != null) {
        booking.setCustomer(dto);
        bookingService.createBooking(booking);
        } else {
        customerService.createCustomer(booking.getCustomer());
        CustomerDto customerDto = customerService.findByPhoneNumber(booking.getCustomer().getPhoneNumber());

        booking.setCustomer(customerDto);
        //log.info(customerDto.getId().toString());
        bookingService.createBooking(booking);
        }

        booking.setRoom(roomService.roomToRoomDto(roomService.getRoomById(booking.getRoom().getId())));
        model.addAttribute("customerName", booking.getCustomer().getName());
        model.addAttribute("roomNumber", booking.getRoom().getRoomNumber());
        model.addAttribute("checkInDate", booking.getCheckInDate());
        model.addAttribute("checkOutDate", booking.getCheckOutDate());

        return "bookingResult";
    }

}
