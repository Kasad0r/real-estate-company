package org.kasador.realestatecompany.domain.spotobjects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Machine extends ParkingSpotObject implements Serializable {
    private Integer weels;

    private MachineType machineType;

    private EngineType engineType;

    private Double engineCapacity;

    public Machine(String name, Double spaceOccupation) {
        super(name, spaceOccupation);
    }


}
