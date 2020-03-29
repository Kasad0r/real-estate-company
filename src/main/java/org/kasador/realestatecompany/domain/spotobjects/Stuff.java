package org.kasador.realestatecompany.domain.spotobjects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Stuff extends ParkingSpotObject implements Serializable {
    public Stuff(String name, Double spaceOccupation) {
        super(name, spaceOccupation);
    }

    @Override
    public String toString() {
        return "Stuff name: " + getName() + ", capacity: " + getSpaceOccupation();
    }
}
