package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;
import java.util.Optional;

public interface Pool {

    void add(RentArea rentArea);

    Optional<RentArea> findArea(String id);

    Optional<RentArea> findFree(Class<? extends RentArea> type);

    List<? extends RentArea> getAllPersonRentAreas(Person person);

    void remove(RentArea rentArea);

    List<? extends RentArea> getRentAreas();
}
