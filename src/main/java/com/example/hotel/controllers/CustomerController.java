package com.example.hotel.controllers;

import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    //denna fungerade inte (testade inte curl eller postman dock)
    @DeleteMapping("/customers/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }


    @GetMapping("/delete/{id}")
    public String deleteCustomerViaGet(@PathVariable Long id) {
        try {
            Customer customer = customerRepository.findById(id).get();
            customerRepository.delete(customer);

            return "success! Customer deleted successfully";
        } catch (DataIntegrityViolationException e) {
            return "error cant delete customer while they have active bookings.";
        } catch (RuntimeException e) {
            return "error Customer not found.";
        }
    }

}
