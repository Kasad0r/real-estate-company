package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;

public class BillingServiceImpl implements BillingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

    @Override
    public void sendBilling(Person person, String message) {
        if (person != null) {
            try {
                Path file = Files
                        .createFile(Paths.get(MessageFormat.format("billings/{0}_rent_date{1}.txt", person.getId(), LocalDate.now())));
                Files
                        .writeString(file, message);
                LOGGER.debug(MessageFormat.format("SEND LETTER to:{0}message: {1}", person.getId(), message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
