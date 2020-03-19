package org.kasador.realestatecompany.service;

import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.domain.Reason;

import java.io.IOException;

public interface LetterService {
    void send(RentArea rentArea, Reason reason, String message);

    void removeOldApartmentLetters(RentArea rentArea) throws IOException;
}
