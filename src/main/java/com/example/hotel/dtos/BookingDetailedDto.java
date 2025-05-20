package com.example.hotel.dtos;

import com.example.hotel.models.Customer;
import com.example.hotel.models.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailedDto {
    private Long id;
    private CustomerDto customer;
    private RoomDto room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;

}
