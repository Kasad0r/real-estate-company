package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;
import org.kasador.realestatecompany.exception.TooManyThingsException;
import org.kasador.realestatecompany.service.ParingSpotService;

public class ParingSpotServiceImpl implements ParingSpotService {
    @Override
    public void add(ParkingSpot parkingSpot, ParkingSpotObject obj) throws TooManyThingsException {
        if (parkingSpot.getUsedSpace() + obj.getSpaceOccupation() > parkingSpot.getUsableSpace()) {
            throw new TooManyThingsException();
        }
        parkingSpot.getParkingSpotObject().add(obj);
    }

    @Override
    public void removeStuff(ParkingSpot parkingSpot, ParkingSpotObject obj) {
        parkingSpot.getParkingSpotObject().remove(obj);
    }
}
