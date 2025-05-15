package com.example.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CustomerModel {
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
}
