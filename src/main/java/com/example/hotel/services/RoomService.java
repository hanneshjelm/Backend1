package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Room;

public interface RoomService {

    public RoomDto roomToRoomDto(Room room);

    RoomDetailedDto roomToRoomDetailedDto(Room room);
}
