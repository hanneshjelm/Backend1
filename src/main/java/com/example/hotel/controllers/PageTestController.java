package com.example.hotel.controllers;

import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageTestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/test")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "createCustomerForm";
    }

    @PostMapping("/register")
    public String handleSubmit(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "createCustomerForm";
        }

        customerRepository.save(customer);
        return "redirect:/test";
    }
    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
}