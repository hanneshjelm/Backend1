package com.example.hotel;

import com.example.hotel.enums.RoomType;
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

import java.time.LocalDate;
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
            roomRepository.save(new Room(101, 14, RoomType.SINGLE)),
            roomRepository.save(new Room(102, 14, RoomType.SINGLE)),
            roomRepository.save(new Room(103, 14, RoomType.SINGLE)),
            roomRepository.save(new Room(104, 16, RoomType.SINGLE)),
            roomRepository.save(new Room(105, 16, RoomType.SINGLE)),
            roomRepository.save(new Room(106, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(107, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(108, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(109, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(110, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(111, 24, RoomType.DOUBLE)),
            roomRepository.save(new Room(112, 32, RoomType.DOUBLE)),
            roomRepository.save(new Room(113, 32, RoomType.DOUBLE)),
            roomRepository.save(new Room(114, 32, RoomType.DOUBLE)),
            roomRepository.save(new Room(115, 32, RoomType.DOUBLE))
            );

            List<Customer> customers = List.of(
            customerRepository.save(new Customer("John", "Smith@gmail.com", "010110011")),
            customerRepository.save(new Customer("Jane", "Doe@gmail.com", "2938471029")),
            customerRepository.save(new Customer("Bob", "Bob@gmail.com","029381923")),
            customerRepository.save(new Customer("Louise", "Loulou@gmail.com", "482019283"))
            );

            List<Booking> bookings = List.of(
                    bookingRepository.save(new Booking(customers.get(0), rooms.get(0), LocalDate.of(2024,12,20), LocalDate.of(2024,12,26), 2)),
                    bookingRepository.save(new Booking(customers.get(0), rooms.get(1),LocalDate.of(2025,1,15), LocalDate.of(2025,1,25), 1)),
                    bookingRepository.save(new Booking(customers.get(1), rooms.get(0),LocalDate.of(2025,3,7), LocalDate.of(2025,3,17), 3)),
                    bookingRepository.save(new Booking(customers.get(2), rooms.get(2), LocalDate.of(2025,5,20), LocalDate.now(), 4)),
                    bookingRepository.save(new Booking(customers.get(3), rooms.get(5), LocalDate.of(2025,4,15), LocalDate.of(2025,4,20), 5))
            );
        };
    }
}