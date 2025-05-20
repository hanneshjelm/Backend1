package com.example.hotel.controllers;

import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/rooms")
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

}
