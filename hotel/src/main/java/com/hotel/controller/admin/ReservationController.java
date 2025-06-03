package com.hotel.controller.admin;


import com.hotel.services.customer.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation/{pageNumber}")
    public ResponseEntity<?> getAllReservations(@PathVariable int pageNumber){

        try {
            return ResponseEntity.ok(reservationService.getAllReservations(pageNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something worng");
        }

    }

    @GetMapping("/reservation/{id}/{status}")
    public ResponseEntity<?> changeReservationStatus(@PathVariable Long id, @PathVariable String status){

        boolean success = reservationService.changeReservationStatus(id, status);

        if(success){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something worng");
        }


    }

}
