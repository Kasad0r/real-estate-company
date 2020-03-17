package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.io.IOException;

public interface BillingService {
    void sendBilling(Person person, String message) ;
}
