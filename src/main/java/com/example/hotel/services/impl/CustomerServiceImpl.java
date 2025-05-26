package com.example.hotel.services.impl;
import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final BookingServiceImpl bookingServiceImpl;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(BookingServiceImpl bookingServiceImpl, CustomerRepository customerRepository) {
        this.bookingServiceImpl = bookingServiceImpl;
        this.customerRepository = customerRepository;
    }


    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<CustomerDetailedDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::customerToCustomerDetailedDto).toList();
    }

    @Override
    public String createCustomer(CustomerDto c) {
        customerRepository.save(customerDtoToCustomer(c));
        return "Customer saved";
    }

    @Override
    public CustomerDto findByPhoneNumber(String phoneNumber) {
        //customerRepository.findByPhoneNumber(phoneNumber);
        return customerRepository.findByPhoneNumber(phoneNumber).map(this::customerToCustomerDto).orElse(null);
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer c) {
        return null;
    }

    @Override
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c) {
        return CustomerDetailedDto.builder().id(c.getId()).name(c.getName())
                .email(c.getEmail()).phoneNumber(c.getPhoneNumber())
                .bookings(c.getBookings().stream()
                        .map(bookingServiceImpl::bookingToBookingDto).toList()).build();

    }

    @Override
    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c) {

        return Customer.builder().id(c.getId()).name(c.getName()).email(c.getEmail())
                .phoneNumber(c.getPhoneNumber()).bookings(c.getBookings().stream()
                        .map(bookings -> bookingServiceImpl.findBookingById(bookings.getId())).toList()).build();
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
