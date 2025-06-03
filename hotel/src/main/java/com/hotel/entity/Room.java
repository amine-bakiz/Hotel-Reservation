package com.hotel.entity;


import com.hotel.dto.RoonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ValueGenerationType;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String type;

    private boolean available;


    public RoonDto getRoonDto(){
        RoonDto roonDto = new RoonDto();

        roonDto.setId(id);
        roonDto.setName(name);
        roonDto.setPrice(price);
        roonDto.setType(type);
        roonDto.setAvailable(available);

        return roonDto;
    }

}
