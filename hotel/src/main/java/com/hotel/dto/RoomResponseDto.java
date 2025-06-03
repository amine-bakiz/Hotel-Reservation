package com.hotel.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDto {

    private List<RoonDto> roonDtoList;

    private Integer totalPage;

    private Integer pageNumber;
}
