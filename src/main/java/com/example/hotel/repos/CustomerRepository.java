package com.example.hotel.repos;

import com.example.hotel.models.Customer;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByEmailContainingIgnoreCase(String email);
}