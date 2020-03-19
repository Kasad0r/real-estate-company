package org.kasador.realestatecompany.domain.spotobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ParkingSpotObject implements Serializable {

    private String id;

    private String name;

    private Double spaceOccupation;

    public ParkingSpotObject(String name, Double spaceOccupation) {
        this.name = name;
        this.spaceOccupation = spaceOccupation;
        id = UUID.randomUUID().toString();
    }
}
