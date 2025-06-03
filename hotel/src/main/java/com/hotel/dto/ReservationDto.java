package com.hotel.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.enums.Reservationstatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    private Long price;

    private Reservationstatus reservationstatus;

    private Long roomId;

    private String roomType;

    private String roomName;

    private Long userId;

    private String username;
}
