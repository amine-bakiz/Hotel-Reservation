// CustomerRoomService.java
package com.hotel.services.customer;

import com.hotel.dto.RoomResponseDto;
import com.hotel.dto.RoonDto;
import com.hotel.entity.Room;
import com.hotel.repository.RoomsRepository;
import com.hotel.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// CustomerRoomService.java
@Service
@RequiredArgsConstructor
public class CustomerRoomService {

    private final RoomsRepository roomsRepository;
    private final ReservationRepository reservationRepository;

    public RoomResponseDto getAllRoomsWithAvailability(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomsRepository.findAll(pageable);

        List<RoonDto> dtos = roomPage.getContent().stream()
                .map(this::enrichWithAvailabilityInfo)
                .collect(Collectors.toList());

        RoomResponseDto response = new RoomResponseDto();
        response.setPageNumber(roomPage.getNumber());
        response.setTotalPage(roomPage.getTotalPages());
        response.setRoonDtoList(dtos);

        return response;
    }

    private RoonDto enrichWithAvailabilityInfo(Room room) {
        RoonDto dto = room.getRoonDto();

        LocalDate nextAvailable = calculateNextAvailableDate(room);
        dto.setNextAvailableDate(nextAvailable);
        dto.setCurrentlyAvailable(nextAvailable.isBefore(LocalDate.now()));

        return dto;
    }

    private LocalDate calculateNextAvailableDate(Room room) {
        // Get the last day of current approved reservations
        LocalDate lastOccupiedDay = reservationRepository.findNextOccupiedUntil(room.getId());

        if(lastOccupiedDay != null) {
            // Return day after last checkout
            return lastOccupiedDay.plusDays(1);
        }

        // If no future reservations, available immediately
        return LocalDate.now();
    }
}