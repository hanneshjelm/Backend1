package com.example.hotel.dtos;

import com.example.hotel.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private Long roomId;
    private int guests;
    private LocalDate checkInDate; // vet inte vilken data vi vill se i den lilla Dton men går ju lätt att ändra
    private LocalDate checkOutDate;
}
