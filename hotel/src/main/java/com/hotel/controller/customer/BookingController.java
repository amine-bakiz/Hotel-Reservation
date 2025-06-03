package com.hotel.controller.customer;

import com.hotel.dto.ReservationDto;
import com.hotel.dto.ReservationResponseDto;
import com.hotel.services.customer.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> addbooking(@RequestBody ReservationDto reservationDto){
        System.out.println("Received DTO: " + reservationDto); // Debug dates
        boolean success = bookingService.addReservation(reservationDto);

        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @GetMapping("/bookings/{userId}/{pageNumber}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId, @PathVariable int pageNumber) {
        try {

            System.out.println("Received request for userId=" + userId + ", page=" + pageNumber);
            ReservationResponseDto result = bookingService.getAllReservationByUserId(userId, pageNumber);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }

}
