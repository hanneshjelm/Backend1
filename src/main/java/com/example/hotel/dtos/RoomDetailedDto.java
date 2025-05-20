package com.example.hotel.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDetailedDto {

    private long id;
    private int roomNumber;
    private int size;
    private boolean doubleRoom;
    private List<BookingDto> bookings;

    private int extraBeds;
    private int capacity;

}
