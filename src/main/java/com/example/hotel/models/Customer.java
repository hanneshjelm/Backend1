package com.example.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp="^[A-Öa-ö ]*$", message="only letters for customer name")
    @Size(min=3, message="atleast 3 characters"
    )
    private String name;

    @Pattern(
            regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
            message = "Please provide a valid email address"
    )
    private String email;

    public Customer (String name, String email) {
        this.name = name;
        this.email = email;
    }
    //@OneToMany(mappedBy = "customer")
    //private List<Booking> bookings;
}
