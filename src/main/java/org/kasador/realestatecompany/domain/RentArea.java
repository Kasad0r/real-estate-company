package org.kasador.realestatecompany.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class RentArea implements Serializable {
    //Орендатор
    private Person tenant;

    private LocalDate rentStartDate;

    private LocalDate rentEndDate;

    private final Double usableSpace;

    private Double usedSpace = 0.0;

    private final String id;


    public RentArea(LocalDate rentStartDate, LocalDate rentEndDate, Double usableSpace) {
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.usableSpace = usableSpace;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "RentArea{" +
                "tenant=" + (tenant != null ? tenant.getId() : "") +
                ", rentStartDate=" + rentStartDate +
                ", rentEndDate=" + rentEndDate +
                ", usableSpace=" + usableSpace +
                ", usedSpace=" + usedSpace +
                ", id='" + id + '\'' +
                '}';
    }
}
