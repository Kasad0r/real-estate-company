package org.kasador.realestatecompany.domain;

import java.time.LocalDate;
import java.util.List;

public class ParkingSpot extends RentArea {

    private List<Stuff> stuff;

    public ParkingSpot(LocalDate rentStartDate,
                       LocalDate rentEndDate,
                       Double usableSpace,
                       Double usedSpace) {
        super(rentStartDate, rentEndDate, usableSpace, usedSpace);
    }
}
