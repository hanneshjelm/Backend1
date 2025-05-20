package com.example.hotel.enums;

import lombok.Getter;

public enum RoomType {
    SINGLE (1, "Single room"),
    DOUBLE (2, "Double room"),;

    @Getter
    private final int value;
    @Getter
    private final String type;

    RoomType(int value, String type) {
        this.value = value;
        this.type = type;
    }

}
