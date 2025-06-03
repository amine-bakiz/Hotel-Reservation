package com.hotel.repository;

import com.hotel.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT MAX(r.checkOut) FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.reservationstatus = com.hotel.enums.Reservationstatus.APPROVED " + // Use fully qualified enum
            "AND r.checkOut >= CURRENT_DATE")
    LocalDate findNextOccupiedUntil(@Param("roomId") Long roomId);


    Page<Reservation> findByUserApp_Id(Pageable pageable, Long id);

}
