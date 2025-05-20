package com.example.hotel.services.impl;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import com.example.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public RoomDto roomToRoomDto(Room roomEntity) {
        return RoomDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).doubleRoom(roomEntity.isDoubleRoom()).build();
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room roomEntity) {
        return RoomDetailedDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).size(roomEntity.getSize()).doubleRoom(roomEntity.isDoubleRoom()).build();
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::roomToRoomDto).toList();
    }

    @Override
    public Room roomToRoomDto(RoomDto DtoRoom) {
        return null;
    }

    @Override
    public Room roomToRoomDetailedDto(RoomDetailedDto detailedRoom) {
        return null;
    }
}
