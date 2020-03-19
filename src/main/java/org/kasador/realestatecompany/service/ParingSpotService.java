package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;

public interface ParingSpotService {
    void add(ParkingSpot parkingSpot,ParkingSpotObject obj);

    void removeStuff(ParkingSpot parkingSpot,ParkingSpotObject obj);
}
