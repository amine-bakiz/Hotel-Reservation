package com.hotel.dto;


import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String userId;

    private String userRole;
}
