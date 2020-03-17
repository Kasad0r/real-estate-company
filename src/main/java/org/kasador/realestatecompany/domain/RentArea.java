package org.kasador.realestatecompany.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class RentArea {
    //Орендатор
    private Person tenant;
    private final LocalDate rentStartDate;
    private final LocalDate rentEndDate;
    private final Double usableSpace;

    private Double usedSpace;

    private final String id;


    public RentArea(LocalDate rentStartDate, LocalDate rentEndDate, Double usableSpace, Double usedSpace) {
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.usableSpace = usableSpace;
        this.usedSpace = usedSpace;
        this.id = UUID.randomUUID().toString();
    }
}
