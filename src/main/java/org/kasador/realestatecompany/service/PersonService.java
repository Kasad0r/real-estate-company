package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.exception.PoolException;
import org.kasador.realestatecompany.exception.ProblematicTenantException;
import org.kasador.realestatecompany.exception.TooManyLettersException;

import java.time.LocalDate;

public interface PersonService {

    void addRentArea(Person person, RentArea rentArea) throws ProblematicTenantException, PoolException, TooManyLettersException;


}
