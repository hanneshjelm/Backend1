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
    private int size;
    private boolean isDoubleRoom;
    //private List<BookingDto> rooms; BookingDto behöver skapas


}
