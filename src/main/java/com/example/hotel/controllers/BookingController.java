package com.example.hotel.controllers;

import com.example.hotel.dtos.*;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.models.Booking;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
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
    private final CustomerService customerService;

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

    @RequestMapping ("bookings/{id}/update")
    public String updateBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findBookingById(id);
        if (booking != null) {
            BookingDetailedDto bookingDetailedDto = bookingService.bookingToBookingDetailedDto(booking);
            model.addAttribute("bookingDetailedDto", bookingDetailedDto);
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "updateBooking";
        }
        return "redirect:/bookings";
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
            @ModelAttribute("booking") BookingDetailedDto bookingForm,
            Model model
    ) {
        List<RoomDetailedDto> availableRooms = roomService.getAvailableRooms(bookingForm);
        model.addAttribute("booking", bookingForm);
        model.addAttribute("rooms", availableRooms);
        return "availableRooms";
    }

    @PostMapping("registerBooking")
    public String registerBooking(@ModelAttribute("customer")CustomerDto customer,
                                  @ModelAttribute("booking")BookingDetailedDto booking ,Model model) {

        CustomerDto dto = customerService.findByPhoneNumber(customer.getPhoneNumber());
        if (dto != null) {
        booking.setCustomer(dto);
        createBooking(booking, model);
            System.out.println("yaass queen");
        } else {
        customerService.createCustomer(customer);
        CustomerDto customerDto = customerService.findByPhoneNumber(customer.getPhoneNumber());

        booking.setCustomer(customerDto);
        createBooking(booking, model);
        //booking.setRoom(roomService.getRoomById(booking.getRoom().getId()));
            System.out.println("yasss bich");
        }
        return "";
    }
    /* public Booking(Customer customer, Room room, LocalDate checkInDate, LocalDate checkOutDate, int guests) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
    }*/
}
