package com.example.hotel;

import com.example.hotel.models.Booking;
import com.example.hotel.models.Customer;
import com.example.hotel.models.Room;
import com.example.hotel.repos.BookingRepository;
import com.example.hotel.repos.CustomerRepository;
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
    public CommandLineRunner commandLineRunner(RoomRepository roomRepository, CustomerRepository customerRepository, BookingRepository bookingRepository) {
        return args -> {
            List<Room> rooms = List.of(
            roomRepository.save(new Room(14, false)),
            roomRepository.save(new Room(14, false)),
            roomRepository.save(new Room(14, false)),
            roomRepository.save(new Room(16, false)),
            roomRepository.save(new Room(16, false)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(24, true)),
            roomRepository.save(new Room(32, true)),
            roomRepository.save(new Room(32, true)),
            roomRepository.save(new Room(32, true)),
            roomRepository.save(new Room(32, true))
            );

            List<Customer> customers = List.of(
            customerRepository.save(new Customer("John", "Smith@gmail.com")),
            customerRepository.save(new Customer("Jane", "Doe@gmail.com")),
            customerRepository.save(new Customer("Bob", "Bob@gmail.com")),
            customerRepository.save(new Customer("Louise", "Loulou@gmail.com"))
            );

            List<Booking> bookings = List.of(
                    bookingRepository.save(new Booking(customers.get(0), rooms.get(0))),
                    bookingRepository.save(new Booking(customers.get(0), rooms.get(1))),
                    bookingRepository.save(new Booking(customers.get(1), rooms.get(0)))
            );


        };
    }
}