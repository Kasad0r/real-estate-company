package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.exception.PoolException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentAreaPool implements Pool<RentArea>, Serializable, RentAreaPoolAddon {
    private static final RentAreaPool rentAreaPool = new RentAreaPool();

    private static final List<RentArea> rentAreas = new ArrayList<>();

    private RentAreaPool() {

    }

    public static RentAreaPool getInstance() {
        return rentAreaPool;
    }

    public List<RentArea> getAll() {
        return rentAreas;
    }

    @Override
    public void restore(List<? extends RentArea> poolBackup) {
        rentAreas.addAll(poolBackup);
    }

    @Override
    public void add(RentArea rentArea) {
        if (find(rentArea.getId()).isEmpty()) {
            rentAreas.add(rentArea);
        } else {
            throw new PoolException("RentAre a Already exist " + rentArea);
        }
    }

    @Override
    public Optional<RentArea> find(String id) {
        return rentAreas
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized Optional<RentArea> getFree(Class<? extends RentArea> type) {
        return rentAreas
                .stream()
                .filter(f -> f.getClass().equals(type) && f.getTenant() == null)
                .findAny();
    }

    @Override
    public List<? extends RentArea> getAllPersonRentAreas(Person person) {
        return rentAreas
                .stream()
                .filter(r -> r.getTenant() != null && r.getTenant().getId().equals(person.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends RentArea> getAllFreeRentAreas() {
        return rentAreas
                .stream()
                .filter(r -> r.getTenant() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartment> getAllFreeApartments() {
        return rentAreas
                .stream()
                .filter(r -> r.getTenant() == null && r.getClass().equals(Apartment.class))
                .map(r -> (Apartment) r)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingSpot> getAllFreeParkingSpots() {
        return rentAreas
                .stream()
                .filter(r -> r.getTenant() == null && r.getClass().equals(ParkingSpot.class))
                .map(r -> (ParkingSpot) r)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(RentArea rentArea) {
        rentAreas.remove(rentArea);
    }
}
