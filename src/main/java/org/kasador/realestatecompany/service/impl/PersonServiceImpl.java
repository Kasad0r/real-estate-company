package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.exception.PoolException;
import org.kasador.realestatecompany.exception.ProblematicTenantException;
import org.kasador.realestatecompany.exception.TooManyLettersException;
import org.kasador.realestatecompany.modeling.GlobalTimeTask;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.PersonService;

import java.time.LocalDate;

public class PersonServiceImpl implements PersonService {

    @Override
    public void addRentArea(Person person, RentArea rentArea)
            throws
            ProblematicTenantException,
            PoolException,
            TooManyLettersException {

        if (person.getLetters().size() >= 3) {
            throw new TooManyLettersException("You have more then 3 letters");
        }
        if (countApartments(person) + countParkingSpots(person) <= 5) {
            if (rentArea instanceof Apartment) {
                person.getApartments().add((Apartment) rentArea);
                rentArea.setTenant(person);
                rentArea.setRentStartDate(GlobalTimeTask.getCurrentDate());
                rentArea.setRentEndDate(GlobalTimeTask.getCurrentDate().plusDays(30));
            } else if (rentArea instanceof ParkingSpot) {
                if (!person.getApartments().isEmpty()) {
                    person.getParkingSpot().add((ParkingSpot) rentArea);
                    rentArea.setTenant(person);
                    rentArea.setRentStartDate(GlobalTimeTask.getCurrentDate());
                    rentArea.setRentEndDate(GlobalTimeTask.getCurrentDate().plusDays(30));
                } else {
                    throw new ProblematicTenantException("Need 1 or more Apartments for Parking Spot");
                }
            }
            RentAreaPool.getInstance().find(rentArea.getId()).ifPresent(rentArea1 -> rentArea.setTenant(person));
        } else {
            throw new PoolException("Couldn’t rent more than 5 areas.");
        }
    }


    private Integer countApartments(Person person) {
        return person.getApartments().size();
    }

    private Integer countParkingSpots(Person person) {
        return person.getParkingSpot().size();
    }
}
