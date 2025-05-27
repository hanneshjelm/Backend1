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



    @GetMapping("/customerBooking")
    public String showForm( @ModelAttribute("booking") BookingDetailedDto bookingForm,
                            Model model
    ) {
        CustomerDto customer= new CustomerDto();
        log.info(String.valueOf(bookingForm.getRoom().getId()));
        bookingForm.setRoom(roomService.getRoomById2(bookingForm.getRoom().getId()));
        model.addAttribute("booking", bookingForm);
        return "customerForBooking";
    }

}