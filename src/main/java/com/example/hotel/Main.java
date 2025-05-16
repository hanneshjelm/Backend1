package com.example.hotel;

import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoomRepository roomRepository) {
        return args -> {

            Room singleRoom101 = new Room(14, false);
            Room singleRoom102 = new Room(14, false);
            Room singleRoom103 = new Room(14, false);
            Room singleRoom104 = new Room(16, false);
            Room singleRoom105 = new Room(16, false);
            Room doubleRoom106 = new Room(24, true);
            Room doubleRoom107 = new Room(24, true);
            Room doubleRoom108 = new Room(24, true);
            Room doubleRoom109 = new Room(24, true);
            Room doubleRoom110 = new Room(24, true);
            Room doubleRoom111 = new Room(24, true);
            Room doubleRoom112 = new Room(32, true);
            Room doubleRoom113 = new Room(32, true);
            Room doubleRoom114 = new Room(32, true);
            Room doubleRoom115 = new Room(32, true);

            List<Room> allRooms = List.of(singleRoom101, singleRoom102,singleRoom103, singleRoom104,
                    singleRoom105, doubleRoom106, doubleRoom107, doubleRoom108, doubleRoom109, doubleRoom110,
                    doubleRoom111, doubleRoom112,doubleRoom113,doubleRoom114,doubleRoom115);

            roomRepository.saveAll(allRooms);

        };
    }
}