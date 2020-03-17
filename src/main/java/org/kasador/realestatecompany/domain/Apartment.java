package org.kasador.realestatecompany.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Apartment extends RentArea {

    private final Integer capacity;

    //Only tenant could rent
    private List<ParkingSpot> parkingSpot;

    private List<Person> dwellers;


    public Apartment(Integer capacity,
                     LocalDate rentStartDate,
                     LocalDate rentEndDate,
                     Double usableSpace,
                     Double usedSpace) {
        super(rentStartDate, rentEndDate, usableSpace, usedSpace);
        this.capacity = capacity;
    }
}
