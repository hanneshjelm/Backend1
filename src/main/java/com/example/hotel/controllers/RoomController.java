package com.example.hotel.controllers;

import com.example.hotel.dtos.RoomDto;
import com.example.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/all")
    public String getAllRooms(Model model) {
        List<RoomDto> rooms = roomService.getAllRooms();
        model.addAttribute("roomTitle", "All Rooms");
        model.addAttribute("name", "Vad händer här?");
        model.addAttribute("allRooms", rooms);

        return "rooms";
    }
}
