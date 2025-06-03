package com.hotel.services;


import com.hotel.dto.RoomResponseDto;
import com.hotel.dto.RoonDto;
import com.hotel.entity.Room;
import com.hotel.repository.RoomsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomsRepository repository;

    public boolean PostRoom(RoonDto dto){
        try {
            Room room = new Room();

            room.setName(dto.getName());
            room.setPrice(dto.getPrice());
            room.setType(dto.getType());
            room.setAvailable(true);

            repository.save(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public RoomResponseDto getAllRooms(int pageNumber) {
        // Use org.springframework.data.domain.Pageable instead of java.awt.print.Pageable
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = repository.findAll(pageable);

        RoomResponseDto roomResponseDto = new RoomResponseDto();

        roomResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDto.setTotalPage(roomPage.getTotalPages());
        roomResponseDto.setRoonDtoList(roomPage.stream().map(Room::getRoonDto).collect(Collectors.toList()));

        return roomResponseDto;
    }

    public RoonDto getRoomById(Long id){
        Optional<Room> optionalRoom = repository.findById(id);
        if(optionalRoom.isPresent()){
            return optionalRoom.get().getRoonDto();
        }else {
            throw new EntityNotFoundException("Room not found");
        }
    }

    public boolean updateRoom(Long id, RoonDto roonDto){
        Optional<Room> optionalRoom = repository.findById(id);
        if(optionalRoom.isPresent()){
            Room existingRoom = optionalRoom.get();

            existingRoom.setName(roonDto.getName());
            existingRoom.setPrice(roonDto.getPrice());
            existingRoom.setType(roonDto.getType());

            repository.save(existingRoom);
            return true;
        }else {
            return false;
        }
    }

    public void deleteRoom(Long id){
        Optional<Room> optionalRoom = repository.findById(id);
        if(optionalRoom.isPresent()){
            repository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Room Not Present");
        }
    }
}
