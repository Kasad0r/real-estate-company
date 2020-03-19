package org.kasador.realestatecompany.domain;

import lombok.Getter;
import lombok.Setter;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ParkingSpot extends RentArea implements Serializable {

    private List<ParkingSpotObject> parkingSpotObject;

    public ParkingSpot(LocalDate rentStartDate,
                       LocalDate rentEndDate,
                       Double usableSpace) {
        super(rentStartDate, rentEndDate, usableSpace);
        this.parkingSpotObject = new ArrayList<>();
    }

    public ParkingSpot(LocalDate rentStartDate,
                       LocalDate rentEndDate,
                       Double length,
                       Double width,
                       Double height) {
        this(rentStartDate, rentEndDate, length * width * height);

    }
}
