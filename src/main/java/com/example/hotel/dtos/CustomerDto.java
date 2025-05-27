package com.example.hotel.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    @Pattern(regexp="^[A-Öa-ö ]*$", message="only letters for customer name")
    @Size(min=3, message="At least 3 characters"
    )
    private String name;

    @Pattern(
            regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
            message = "Please provide a valid email address"
    )
    private String email;

    @Pattern(regexp = "\\+?[0-9 ]{8,15}", message = "Invalid phone number format")
    private String phoneNumber;

}
