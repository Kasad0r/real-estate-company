package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.*;
import org.kasador.realestatecompany.domain.spotobjects.Machine;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;
import org.kasador.realestatecompany.service.LetterService;
import org.kasador.realestatecompany.service.RentAreaService;

import java.io.IOException;
import java.util.ArrayList;

public class RentAreaServiceImpl implements RentAreaService {
    private LetterService letterService;

    public RentAreaServiceImpl(LetterService letterService) {
        this.letterService = letterService;
    }

    @Override
    public void removeDweller(Apartment apartment, Person person) {
        apartment.getDwellers().remove(person);
    }

    @Override
    public void evictRentArea(RentArea rentArea) {
        try {
            letterService.removeOldApartmentLetters(rentArea);
        } catch (IOException e) {
            System.out.println("No letter to remove...");
        }
        if (rentArea instanceof Apartment) {
            ((Apartment) rentArea).setDwellers(new ArrayList<>());
            rentArea.getTenant().getApartments().remove(rentArea);
        }
        if (rentArea instanceof ParkingSpot) {
            rentArea.getTenant().getParkingSpot().remove(rentArea);
        }
        rentArea.setTenant(null);

    }

    @Override
    public void extendRent(RentArea rentArea, int days) {
        rentArea.setRentEndDate(rentArea.getRentEndDate().plusDays(days));
    }

    @Override
    public void addDweller(Apartment a, Person dwellerToAdd) {
        a.getDwellers().add(dwellerToAdd);
    }

    @Override
    public void autoEvictRentArea(RentArea rentArea) {
        if (rentArea instanceof ParkingSpot) {
            ParkingSpot parkSpot = (ParkingSpot) rentArea;
            for (ParkingSpotObject parObj : parkSpot.getParkingSpotObject()) {
                if (parObj instanceof Machine) {
                    parkSpot.getParkingSpotObject().remove(parObj);
                    letterService.send(rentArea, Reason.CAR_SOLD, "We sold your car and extent your rent for 2 months.");
                    extendRent(rentArea, 60);
                    break;
                }
            }
        }
    }
}
