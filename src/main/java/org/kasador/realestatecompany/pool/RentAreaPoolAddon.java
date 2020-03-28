package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;
import java.util.Optional;

public interface RentAreaPoolAddon {
    Optional<RentArea> getFree(Class<? extends RentArea> type);

    List<? extends RentArea> getAllPersonRentAreas(Person person);

    List<? extends RentArea> getAllFreeRentAreas();

    List<Apartment> getAllFreeApartments();

    List<ParkingSpot> getAllFreeParkingSpots();

}
