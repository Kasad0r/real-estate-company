package org.kasador.realestatecompany.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kasador.realestatecompany.tools.AddressMockGenerator;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Person implements Serializable {
    private String id;

    private String name;

    private String surname;

    private LocalDate birthday;

    private List<Apartment> apartments;

    private List<File> letters;

    private String address;

    private List<ParkingSpot> parkingSpot;

    public Person() {
        letters = new ArrayList<>();
        parkingSpot = new ArrayList<>();
        apartments = new ArrayList<>();
        id = UUID.randomUUID().toString();
        address = new AddressMockGenerator().generateAddress();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", apartments=" + apartments +
                ", letters=" + letters +
                ", address='" + address + '\'' +
                ", parkingSpot=" + parkingSpot +
                '}';
    }
}
