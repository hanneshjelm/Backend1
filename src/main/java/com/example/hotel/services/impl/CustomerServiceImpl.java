package com.example.hotel.services.impl;
import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.mappers.CustomerMapper;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository,
        CustomerMapper customerMapper ) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerDto customerToCustomerDto(Customer c) {
        return customerMapper.customerToCustomerDto(c);
    }

    @Override
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer c) {
        return customerMapper.customerToCustomerDetailedDto(c);
    }

    @Override
    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto c) {
        return customerMapper.customerDetailedDtoToCustomer(c);
    }

    @Override
    public List<CustomerDetailedDto> getAllCustomers() {

        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDetailedDto).toList();
    }

    @Override
    @Transactional
    public String createCustomer(CustomerDto c) {
        customerRepository.save(customerMapper.customerDtoToCustomer(c));
        return "Success";
    }

    @Override
    public List<CustomerDetailedDto> findCustomerByEmail(String email) {
        List<Customer> customers = customerRepository.findByEmailContainingIgnoreCase(email);
        if(customers.isEmpty()) {
            return List.of();
        }
        return customers.stream().map(customerMapper::customerToCustomerDetailedDto).toList();
    }

    @Override
    public CustomerDto findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).map(customerMapper::customerToCustomerDto).orElse(null);
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDto c) {
        return customerMapper.customerDtoToCustomer(c);
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