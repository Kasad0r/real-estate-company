package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.time.LocalDate;

public interface PersonService {
    void removeRentArea(Person person, RentArea rentArea);

    void addRentArea(Person person, RentArea rentArea);

    Person createPerson(String name, String surname, LocalDate birthday);


}
