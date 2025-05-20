package com.example.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    private boolean doubleRoom;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room(int roomNumber, int size, boolean doubleRoom) {
        this.roomNumber = roomNumber;
        this.size = size;
        this.doubleRoom = doubleRoom;
    }

}
