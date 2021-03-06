package org.kasador.realestatecompany.domain.spotobjects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Machine extends ParkingSpotObject implements Serializable {
    private Integer weels;

    private MachineType machineType;

    public Machine(String name, Double spaceOccupation, Integer weels, MachineType machineType, EngineType engineType, Double engineCapacity) {
        super(name, spaceOccupation);
        this.weels = weels;
        this.machineType = machineType;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
    }

    private EngineType engineType;

    private Double engineCapacity;

    public Machine(String name, Double spaceOccupation) {
        super(name, spaceOccupation);
    }

    @Override
    public String toString() {
        return "Machine name:" + getName() + ", machineType:" + machineType + ", engineType:" + engineType + ", engineCapacity:" + engineCapacity;
    }
}
