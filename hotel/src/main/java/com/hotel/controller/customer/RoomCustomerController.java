package com.hotel.controller.customer;

import com.hotel.services.customer.CustomerRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class RoomCustomerController {

    private final CustomerRoomService customerRoom;

    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAvaialbleRooms(@PathVariable int pageNumber){
        return ResponseEntity.ok(customerRoom.getAllRoomsWithAvailability(pageNumber));
    }
}
