package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;

interface Restorer {

    List<? extends RentArea> getRentAreas();

    List<Person> getPersons();
}
