package com.hotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.dto.ReservationDto;
import com.hotel.enums.Reservationstatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long price;

    private Reservationstatus reservationstatus;

    @ManyToOne
    private Room room;

    @ManyToOne
    private UserApp userApp;

    public ReservationDto getReservationDto(){
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId(id);
        reservationDto.setPrice(price);
        reservationDto.setCheckIn(checkIn);
        reservationDto.setCheckOut(checkOut);
        reservationDto.setReservationstatus(reservationstatus);

        reservationDto.setUserId(userApp.getId());
        reservationDto.setUsername(userApp.getUsername());

        reservationDto.setRoomId(room.getId());
        reservationDto.setRoomName(room.getName());
        reservationDto.setRoomType(room.getType());

        return reservationDto;
    }

}
