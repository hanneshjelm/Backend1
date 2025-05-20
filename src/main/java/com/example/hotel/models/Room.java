package com.example.hotel.models;

import com.example.hotel.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private long id;

    private int roomNumber;

    private int size;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room(int roomNumber, int size, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.size = size;
        this.roomType = roomType;
    }

}
