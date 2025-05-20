package com.example.hotel.dtos;

import com.example.hotel.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private long id;
    private int roomNumber;
    private RoomType roomType;

}
