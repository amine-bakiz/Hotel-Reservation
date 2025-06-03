package com.hotel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoonDto {

    private Long id;

    private String name;

    private Long price;

    private String type;

    private boolean available;

    private LocalDate nextAvailableDate;

    private boolean currentlyAvailable;


}
