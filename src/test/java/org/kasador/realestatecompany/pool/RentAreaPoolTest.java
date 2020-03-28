package org.kasador.realestatecompany.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;
import java.util.Optional;

class RentAreaPoolTest {
    private RentAreaPool rentAreaPool;
    private Person person;

    @BeforeEach
    public void init() {
        rentAreaPool = RentAreaPool.getInstance();
        person = new Person();
        person.setName("Anton");
        person.setSurname("Smith");
        Apartment apartment = new Apartment(null, null, 30.0);
        person.getApartments().add(apartment);
        ParkingSpot parkingSpot = new ParkingSpot(null, null, 10.0);
        person.getParkingSpot().add(parkingSpot);
        rentAreaPool.add(apartment);
        rentAreaPool.add(parkingSpot);
    }

    @Test
    public void tryGetParkingSpotFromPool() {
        Optional<RentArea> free = rentAreaPool.getFree(ParkingSpot.class);
        Assertions.assertTrue(free.isPresent());
        Assertions.assertEquals(ParkingSpot.class, free.get().getClass());
    }

    @Test
    public void tryGetApartmentsFromPool() {
        Optional<RentArea> free = rentAreaPool.getFree(Apartment.class);
        Assertions.assertTrue(free.isPresent());
        Assertions.assertEquals(Apartment.class, free.get().getClass());
    }

    @Test
    public void tryGetAllPersonRentAreas() {
        rentAreaPool.getFree(Apartment.class).get().setTenant(person);
        rentAreaPool.getFree(ParkingSpot.class).get().setTenant(person);
        List<? extends RentArea> allPersonRentAreas = rentAreaPool.getAllPersonRentAreas(person);
        Assertions.assertEquals(2, allPersonRentAreas.size());
    }

}