package com.example.hotel.services.impl;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.BookingDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.mappers.BookingMapper;
import com.example.hotel.mappers.CustomerMapper;
import com.example.hotel.models.Booking;
import com.example.hotel.models.Customer;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
import com.example.hotel.services.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              CustomerRepository customerRepository,
                              BookingMapper bookingMapper,
                              RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.bookingMapper = bookingMapper;
        this.roomRepository  = roomRepository;
    }

    @Override
    public BookingDto bookingToBookingDto(Booking b) {
        return bookingMapper.bookingToBookingDto(b);
    }

    @Override
    public BookingDetailedDto bookingToBookingDetailedDto(Booking b) {
        return bookingMapper.bookingToBookingDetailedDto(b);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::bookingToBookingDto).toList();
    }

    @Override
    public String updateBooking(BookingDetailedDto b) {
        Booking existingBooking = bookingRepository.findById(b.getId()).orElse(null);
        Customer customer = customerRepository.findById(b.getCustomer().getId()).orElse(null);
        Room room = roomRepository.findById(b.getRoom().getId()).orElse(null);

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
        Customer customer = customerRepository.findById(bookingDetailedDto.getCustomer().getId()).orElse(null);
        Room room = roomRepository.findById(bookingDetailedDto.getRoom().getId()).orElse(null);

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
    public Booking findBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public BookingDetailedDto findBookingDetailedDtoById(Long id) {
        Booking existingBooking = bookingRepository.findById(id).get();
        return bookingMapper.bookingToBookingDetailedDto(existingBooking);
    }



}
