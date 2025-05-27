package com.example.hotel.dtos;

import com.example.hotel.models.Booking;
import jakarta.validation.Valid;
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
    @Valid
    private CustomerDto customer;
    private RoomDto room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;

    public BookingDetailedDto(CustomerDto customer, RoomDto room, LocalDate checkInDate, LocalDate checkOutDate, int guests) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
    }

}
