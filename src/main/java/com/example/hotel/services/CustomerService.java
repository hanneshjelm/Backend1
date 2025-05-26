package com.example.hotel.services;

import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;

import java.util.List;

public interface CustomerService {

    public CustomerDto customerToCustomerDto(Customer c);
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c);

    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c);

    public List<CustomerDetailedDto> getAllCustomers();

    public Customer findCustomerById(Long id);

    public String updateCustomer(CustomerDetailedDto c);
    public CustomerDto findByPhoneNumber(String phoneNumber);
    public Customer customerDtoToCustomer(CustomerDto c);
    public String createCustomer(CustomerDto c);

}
