package com.hotel.services.customer;


import com.hotel.dto.ReservationResponseDto;
import com.hotel.entity.Reservation;
import com.hotel.entity.Room;
import com.hotel.enums.Reservationstatus;
import com.hotel.repository.ReservationRepository;
import com.hotel.repository.RoomsRepository;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomsRepository roomsRepository;

    public static final int RESULT_PER_PAGE = 4;

    public ReservationResponseDto getAllReservations(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream()
                .map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }


    // In ReservationService.java
    public boolean changeReservationStatus(Long id, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            Room existingRoom = existingReservation.getRoom();

            if (Objects.equals(status, "approved")) {
                existingReservation.setReservationstatus(Reservationstatus.APPROVED);
                existingRoom.setAvailable(false); // Room becomes unavailable when approved
            } else {
                existingReservation.setReservationstatus(Reservationstatus.REJECTED);
                existingRoom.setAvailable(true); // Room becomes available when rejected
            }

            reservationRepository.save(existingReservation);
            roomsRepository.save(existingRoom);
            return true;
        }
        return false;
    }
}
