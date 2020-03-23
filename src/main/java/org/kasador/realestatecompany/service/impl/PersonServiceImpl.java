package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.exception.PoolException;
import org.kasador.realestatecompany.exception.ProblematicTenantException;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.service.PersonService;

import java.time.LocalDate;

public class PersonServiceImpl implements PersonService {
    private final PersonPool personPool;

    public PersonServiceImpl() {
        this.personPool = PersonPool.getInstance();
    }

    @Override
    public void removeRentArea(Person person, RentArea rentArea) {
        if (rentArea instanceof Apartment) {
            person.getApartments().remove(rentArea);
        } else if (rentArea instanceof ParkingSpot) {
            person.getParkingSpot().remove(rentArea);
        }
    }
/*
    @Override
    public void addLetter(Person person) {

    }

    @Override
    public void removeLetters(Person person) {
        person.getLetters().clear();
    }

 */
    @Override
    public void addRentArea(Person person, RentArea rentArea) {
        if (countApartments(person) + countParkingSpots(person) < 5) {
            if (rentArea instanceof Apartment) {
                person.getApartments().add((Apartment) rentArea);
            } else if (rentArea instanceof ParkingSpot) {
                if (!person.getApartments().isEmpty()) {
                    person.getParkingSpot().add((ParkingSpot) rentArea);
                } else throw new ProblematicTenantException("Need 1 or more Apartments for Parking Spot");
            }
        } else {
            throw new PoolException("Couldn’t rent more than 5 areas.");
        }
    }

    @Override
    public Person createPerson(String name, String surname, LocalDate birthday) {
        var per = new Person();
        per.setSurname(surname);
        per.setName(name);
        per.setBirthday(birthday);
        personPool.add(per);
        return per;
    }

    private Integer countApartments(Person person) {
        return person.getApartments().size();
    }

    private Integer countParkingSpots(Person person) {
        return person.getParkingSpot().size();
    }
}
