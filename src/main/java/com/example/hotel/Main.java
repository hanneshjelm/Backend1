package com.example.hotel;

import com.example.hotel.models.Room;
import com.example.hotel.repos.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoomRepository roomRepository) {
        return args -> {
            roomRepository.save(new Room(14, false));
            roomRepository.save(new Room(14, false));
            roomRepository.save(new Room(14, false));
            roomRepository.save(new Room(16, false));
            roomRepository.save(new Room(16, false));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(24, true));
            roomRepository.save(new Room(32, true));
            roomRepository.save(new Room(32, true));
            roomRepository.save(new Room(32, true));
            roomRepository.save(new Room(32, true));
        };
    }
}