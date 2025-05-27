package com.example.hotel.repos;

import com.example.hotel.dtos.RoomDetailedDto;
import com.example.hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT r FROM Room r
        WHERE r NOT IN (
              SELECT b.room FROM Booking b
              WHERE b.checkInDate <= :checkOut
                AND b.checkOutDate >= :checkIn
          )
    """)
    List<Room> findAvailableRooms(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
           // @Param("guestRequirement") int guestRequirement
    );
}

/*

@Query("""
        SELECT r FROM Room r
        WHERE r.capacity >= :guestRequirement
          AND r NOT IN (
              SELECT b.room FROM Booking b
              WHERE b.checkInDate < :checkOut
                AND b.checkOutDate > :checkIn
          )
    """)

 */