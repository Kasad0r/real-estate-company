package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

public interface RentAreaService {
    void removeDweller(Apartment apartment, Person person);

    void evictRentArea(RentArea apartment);

    void extendRent(RentArea rentArea, int days);

    void addDweller(Apartment a, Person dwellerToAdd);

    void autoEvictRentArea(RentArea rentArea);
}
