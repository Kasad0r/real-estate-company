package org.kasador.realestatecompany.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Apartment extends RentArea implements Serializable {

    private List<Person> dwellers;

    public Apartment(
            LocalDate rentStartDate,
            LocalDate rentEndDate,
            Double usableSpace) {
        super(rentStartDate, rentEndDate, usableSpace);
        this.dwellers = new ArrayList<>();
    }

    public Apartment(
            LocalDate rentStartDate,
            LocalDate rentEndDate, Double length,
            Double width,
            Double height) {
        this(rentStartDate, rentEndDate, length * width * height);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "dwellers=" + dwellers +
                '}';
    }
}
