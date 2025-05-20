package com.example.hotel.services;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Room;

import java.util.List;


public interface RoomService {

    public RoomDto roomToRoomDto(Room room);

    public RoomDetailedDto roomToRoomDetailedDto(Room room);

    public List<RoomDto> getAllRooms();

    public RoomDetailedDto getRoomById(long id);
}
