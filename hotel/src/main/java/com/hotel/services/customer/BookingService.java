package com.hotel.services.customer;


import com.hotel.dto.ReservationDto;
import com.hotel.dto.ReservationResponseDto;
import com.hotel.entity.Reservation;
import com.hotel.entity.Room;
import com.hotel.entity.UserApp;
import com.hotel.enums.Reservationstatus;
import com.hotel.repository.ReservationRepository;
import com.hotel.repository.RoomsRepository;
import com.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hotel.services.customer.ReservationService.RESULT_PER_PAGE;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;

    private final RoomsRepository roomsRepository;

    private final ReservationRepository reservationRepository;

    public boolean addReservation(ReservationDto reservationDto){
        Optional<UserApp> optionalUserApp = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom = roomsRepository.findById(reservationDto.getRoomId());

        if(optionalRoom.isPresent() && optionalUserApp.isPresent()){
            Reservation reservation = new Reservation();

            reservation.setRoom(optionalRoom.get());
            reservation.setUserApp(optionalUserApp.get());
            reservation.setCheckIn(reservationDto.getCheckIn());
            reservation.setCheckOut(reservationDto.getCheckOut());
            reservation.setReservationstatus(Reservationstatus.PENDING);

            if (reservationDto.getCheckIn() != null && reservationDto.getCheckOut() != null) {
                long days = ChronoUnit.DAYS.between(reservationDto.getCheckIn(), reservationDto.getCheckOut());
                reservation.setPrice(optionalRoom.get().getPrice() * days);
            } else {
                throw new IllegalArgumentException("Check-in or check-out date is null");
            }

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public ReservationResponseDto getAllReservationByUserId(Long id,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findByUserApp_Id(pageable,id);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream()
                .map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
}
