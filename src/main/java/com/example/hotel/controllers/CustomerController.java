package com.example.hotel.controllers;

import com.example.hotel.models.CustomerModel;
import com.example.hotel.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public CustomerModel createCustomer(@RequestBody CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @GetMapping
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    @DeleteMapping("/api/customers/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }



}
