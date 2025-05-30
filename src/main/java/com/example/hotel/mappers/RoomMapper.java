package com.example.hotel.mappers;


import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.dtos.RoomDto;
import com.example.hotel.enums.RoomType;
import com.example.hotel.models.Booking;
import com.example.hotel.models.Room;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoomMapper {

    private final BookingMapper bookingMapper;

    public RoomMapper(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }



    public RoomDto roomToRoomDto(Room roomEntity) {
        return RoomDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber()).roomTypeString(roomEntity.getRoomType().getType()).build();
    }

    public RoomDetailedDto roomToRoomDetailedDto(Room roomEntity) {

        List<Booking> bookings = roomEntity.getBookings();
        if(bookings == null || bookings.isEmpty()){
            bookings = Collections.emptyList();
        }

        RoomDetailedDto roomDetailedDto = RoomDetailedDto.builder().id(roomEntity.getId()).roomNumber(roomEntity.getRoomNumber())
                .size(roomEntity.getSize()).roomTypeString(roomEntity.getRoomType().getType()).bookings(bookings
                        .stream().map(bookingMapper::bookingToBookingDto).toList()).build();

        roomDetailedDto.setExtraBeds(amountOfExtraBeds(roomEntity));
        roomDetailedDto.setCapacity(totalCapacity(roomEntity));
        return roomDetailedDto;
    }

    public int amountOfExtraBeds(Room room){
        if (room.getRoomType() == RoomType.SINGLE){
            return 0;
        }

        if(room.getSize() < 22){
            return 0;
        } else if(room.getSize() < 30){
            return 1;
        } else {
            return 2;
        }
    }

    public int totalCapacity(Room room){
        if(room.getRoomType() == RoomType.DOUBLE){
            return RoomType.DOUBLE.getValue() + amountOfExtraBeds(room);
        }

        return room.getRoomType().getValue();
    }
}
