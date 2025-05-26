package com.example.hotel.controllers;
import com.example.hotel.dtos.CustomerDetailedDto;
import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import com.example.hotel.services.BookingService;
import com.example.hotel.services.impl.CustomerServiceImpl;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingService bookingService;


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

    @GetMapping("/createForm")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDetailedDto());
        return "createCustomerForm";
    }

    @PostMapping("/create")
    public String createCustomer(CustomerDetailedDto customerDetailedDto) {
        customerService.createCustomer(customerDetailedDto);
        return "redirect:/customers/all";
    }

}
