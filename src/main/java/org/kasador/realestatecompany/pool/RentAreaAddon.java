package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;
import java.util.Optional;

public interface RentAreaAddon {
    Optional<RentArea> findFree(Class<? extends RentArea> type);

    List<? extends RentArea> getAllPersonRentAreas(Person person);
}
