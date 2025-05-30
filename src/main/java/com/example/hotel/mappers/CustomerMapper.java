package com.example.hotel.mappers;

import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.BookingRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerMapper {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    public CustomerMapper(BookingMapper bookingMapper, BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }
    public CustomerDto customerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).name(c.getName())
                .email(c.getEmail()).phoneNumber(c.getPhoneNumber())
                .build();

    }

    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c) {
        return CustomerDetailedDto.builder().id(c.getId()).name(c.getName())
                .email(c.getEmail()).phoneNumber(c.getPhoneNumber())
                .bookings(c.getBookings().stream()
                        .map(bookingMapper::bookingToBookingDto).toList()).build();

    }

    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c) {

        return Customer.builder().id(c.getId()).name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).bookings(c.getBookings().stream()
                        .map(bookings -> bookingRepository.findById(bookings.getId()).orElse(null)).toList()).build();
    }

    public Customer customerDtoToCustomer(CustomerDto c) {

        return Customer.builder().name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).build();
    }
}
