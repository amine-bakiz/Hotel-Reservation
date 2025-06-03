package com.hotel.dto;


import lombok.Data;

import java.util.List;

@Data
public class ReservationResponseDto {
    private int totalPages;

    private int pageNumber;

    private List<ReservationDto> reservationDtoList;
}
