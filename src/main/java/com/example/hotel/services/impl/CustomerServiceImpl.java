package com.example.hotel.services.impl;
import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingService bookingService;

    public CustomerServiceImpl(CustomerRepository customerRepository, @Lazy BookingService bookingService) {
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
    }


    @Override
    public List<CustomerDetailedDto> getAllCustomers() {

        return customerRepository.findAll().stream().map(this::customerToCustomerDetailedDto).toList();
    }

    @Override
    @Transactional
    public String createCustomer(CustomerDto c) {
        customerRepository.save(customerDtoToCustomer(c));
        return "Success";
    }

    @Override
    public List<CustomerDetailedDto> findCustomerByEmail(String email) {
        List<Customer> customers = customerRepository.findByEmailContainingIgnoreCase(email);
        if(customers.isEmpty()) {
            return List.of();
        }
        return customers.stream().map(this::customerToCustomerDetailedDto).toList();
    }

    @Override
    public CustomerDto findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).map(this::customerToCustomerDto).orElse(null);
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).name(c.getName())
                .email(c.getEmail()).phoneNumber(c.getPhoneNumber())
                .build();

    }

    @Override
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c) {
        return CustomerDetailedDto.builder().id(c.getId()).name(c.getName())
                .email(c.getEmail()).phoneNumber(c.getPhoneNumber())
                .bookings(c.getBookings().stream()
                        .map(bookingService::bookingToBookingDto).toList()).build();

    }

    @Override
    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c) {

        return Customer.builder().id(c.getId()).name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).bookings(c.getBookings().stream()
                        .map(bookings -> bookingService.findBookingById(bookings.getId())).toList()).build();
    }

    public Customer customerDtoToCustomer(CustomerDto c) {

        return Customer.builder().name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).build();
    }

    //kan ta bort denna om inte room eller booking ska ha builders
    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public String updateCustomer(CustomerDetailedDto c) {
        Customer existingCustomer = findCustomerById(c.getId());
        if (existingCustomer != null) {

            existingCustomer.setName(c.getName());
            existingCustomer.setEmail(c.getEmail());
            existingCustomer.setPhoneNumber(c.getPhoneNumber());

            customerRepository.save(existingCustomer);
            return "Customer updated successfully";
        }
        else {
            return "Customer not found";
        }

    }
}