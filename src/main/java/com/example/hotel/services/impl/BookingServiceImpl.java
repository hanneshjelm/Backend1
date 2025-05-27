package com.example.hotel.services.impl;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Booking;
import com.example.hotel.models.Customer;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              CustomerService customerService,
                              RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::bookingToBookingDto).toList();
    }

    @Override
    public String updateBooking(BookingDetailedDto b) {
        Booking existingBooking = bookingRepository.findById(b.getId()).orElse(null);
        Customer customer = customerService.findCustomerById(b.getCustomer().getId());
        Room room = roomService.getRoomById(b.getRoom().getId());

        if (existingBooking != null) {
            existingBooking.setRoom(room);
            existingBooking.setGuests(b.getGuests());
            existingBooking.setCustomer(customer);
            existingBooking.setCheckInDate(b.getCheckInDate());
            existingBooking.setCheckOutDate(b.getCheckOutDate());
            bookingRepository.save(existingBooking);
        }
        else {
            return "Booking not found";
        }
        return "Booking updated";
    }

    @Override
    public String createBooking(BookingDetailedDto bookingDetailedDto) {
        Customer customer = customerService.findCustomerById(bookingDetailedDto.getCustomer().getId());
        Room room = roomService.getRoomById(bookingDetailedDto.getRoom().getId());

        if (customer == null) {
            return "Customer not found";
        }
        if (room == null) {
            return "Room not found";
        }
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setGuests(bookingDetailedDto.getGuests());
        booking.setCheckInDate(bookingDetailedDto.getCheckInDate());
        booking.setCheckOutDate(bookingDetailedDto.getCheckOutDate());
        bookingRepository.save(booking);

        return "Booking created";
    }


    @Override
    public BookingDto bookingToBookingDto(Booking b) {
        return BookingDto.builder().id(b.getId()).guests(b.getGuests()).checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public BookingDetailedDto bookingToBookingDetailedDto(Booking b) {
        return BookingDetailedDto.builder().id(b.getId()).customer(new CustomerDto(b.getCustomer().getId(), b.getCustomer().getName(), b.getCustomer().getEmail(), b.getCustomer().getPhoneNumber()))
                .room(new RoomDto(b.getRoom().getId(), b.getRoom().getRoomNumber(), b.getRoom().getRoomType().getType())).guests(b.getGuests())
                .checkInDate(b.getCheckInDate()).checkOutDate(b.getCheckOutDate()).build();
    }


    /*
    public Booking BookingDetailedDtoToBooking(BookingDetailedDto b) {
        return Booking.builder().id(b.getId()).customer(customerService.findCustomerById(b.getId()))
               // .room(b.getRoom()).checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate()).guests(b.getGuests())
                .build(); }
*/

    @Override
    public Booking findBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }




}
