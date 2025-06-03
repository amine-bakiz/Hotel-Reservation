package com.hotel.repository;

import com.hotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomsRepository extends JpaRepository<Room,Long>{

    Page<Room> findByAvailable(Boolean available, Pageable pageable);
}
