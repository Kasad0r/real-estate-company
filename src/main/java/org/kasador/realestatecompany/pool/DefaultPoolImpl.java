package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultPoolImpl implements Pool {
    private static final DefaultPoolImpl defaultPool = new DefaultPoolImpl();

    private static final List<RentArea> rentAreas = new ArrayList<>();

    private DefaultPoolImpl() {

    }

    public static DefaultPoolImpl getInstance() {
        return defaultPool;
    }

    public List<RentArea> getRentAreas() {
        return rentAreas;
    }

    @Override

    public void add(RentArea rentArea) {
        if (findArea(rentArea.getId()).isEmpty()) {
            rentAreas.add(rentArea);
        } else {
            throw new PresentRentAreaException("Already exist");
        }
    }

    @Override
    public Optional<RentArea> findArea(String id) {
        return rentAreas.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<RentArea> findFree(Class<? extends RentArea> type) {
        return rentAreas.stream().filter(f -> f.getClass().equals(type) && f.getTenant() == null).findAny();
    }

    @Override
    public List<? extends RentArea> getAllPersonRentAreas(Person person) {
        return rentAreas.stream().filter(r -> r.getTenant().equals(person)).collect(Collectors.toList());
    }

    @Override
    public void remove(RentArea rentArea) {
        rentAreas.remove(rentArea);
    }
}
