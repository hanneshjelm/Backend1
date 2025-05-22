package com.example.hotel.controllers;

import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private CustomerRepository customerRepository;



    @RequestMapping("/allcustomers")
    public List<CustomerDetailedDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //Denna måste flyttas Tomas
    @DeleteMapping("/customers/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }



}
