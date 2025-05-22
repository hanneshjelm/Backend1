package com.example.hotel.services;

import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerService {

    public CustomerDto customerToCustomerDto(Customer c);
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c);

    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c);

    public List<CustomerDetailedDto> getAllCustomers();

    public String createCustomer(CustomerDetailedDto c);

    public Customer findCustomerById(Long id);

    public String updateCustomer(CustomerDetailedDto c);

}
