package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;
import org.kasador.realestatecompany.exception.TooManyThingsException;

public interface ParingSpotService {
    void add(ParkingSpot parkingSpot,ParkingSpotObject obj) throws TooManyThingsException;

    void removeStuff(ParkingSpot parkingSpot,ParkingSpotObject obj);
}
