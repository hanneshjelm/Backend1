package com.example.hotel.controllers;

import com.example.hotel.dtos.BookingDetailedDto;
import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.dtos.CustomerDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.RoomService;
import com.example.hotel.services.impl.CustomerServiceImpl;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger log = Logger.getLogger(BookingController.class.getName());
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoomService roomService;



    @RequestMapping("/all")
    public String getAllBookings(Model model) {
        List<CustomerDetailedDto> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable long id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            CustomerDetailedDto customerDetailedDto = customerService.customerToCustomerDetailedDto(customer);
            model.addAttribute("customerDetailedDto", customerDetailedDto);
            return "customerDetails";
        }
        return "redirect:/customers/all";
    }


    /*
    @RequestMapping("/allcustomers")
    public List<CustomerDetailedDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }


     */
    @GetMapping("/search")
    public String search(@RequestParam("keyword") @Email String keyword, Model model) {
        List<CustomerDetailedDto> customers = customerService.findCustomerByEmail(keyword);
        model.addAttribute("customers", customers);
        return "customers";
    }


    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerRepository.deleteById(id);
            return "redirect:/customers/all";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete a customer with an active booking");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer not found");
        }
        return "redirect:/customers/all";
    }



    @GetMapping("/deleteCustomerFromDetails/{id}")
    public String deleteCustomer2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerRepository.deleteById(id);
            return "redirect:/customers/all";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete a customer with an active booking");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer not found");
        }
        return "redirect:/customers/{id}";
    }

    @GetMapping("/{id}/update")
    public String updateCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            CustomerDetailedDto customerDetailedDto = customerService.customerToCustomerDetailedDto(customer);
            model.addAttribute("customerDetailedDto", customerDetailedDto);
            return "updateCustomerForm";
        }
        return "redirect:/customers/all";
    }


    @PostMapping("/update")
    public String updateCustomer(CustomerDetailedDto customerDetailedDto) {
        customerService.updateCustomer(customerDetailedDto);
        return "redirect:/customers/all";
    }


    @GetMapping("/customerBooking")
    public String showForm( @ModelAttribute("booking") BookingDetailedDto bookingForm,
                            Model model
    ) {

        model.addAttribute("booking", bookingForm);
        return "customerForBooking";
    }

}