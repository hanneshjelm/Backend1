package com.example.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private long id;

    private int size;

    private boolean isDoubleRoom;

    public Room(int size, boolean isDoubleRoom) {
        this.size = size;
        this.isDoubleRoom = isDoubleRoom;
    }

}
