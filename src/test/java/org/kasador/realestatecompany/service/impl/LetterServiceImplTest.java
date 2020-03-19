package org.kasador.realestatecompany.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.Reason;
import org.kasador.realestatecompany.service.LetterService;

import java.time.LocalDate;
import java.util.UUID;


public class LetterServiceImplTest {

    @Test
    public void send() {
        Apartment apartment = new Apartment(LocalDate.now(), LocalDate.now().plusDays(50), 30.0);
        LetterService letterService = new LetterServiceImpl();
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setAddress("12 Test st.");
        person.setName("Peter");
        person.setSurname("Parker");
        apartment.setTenant(person);
        letterService.send(apartment, Reason.EVICTION_LETTER, "TEST EVICTION");
        Assert.assertEquals(1, person.getLetters().size());
        Assert.assertNotEquals(person.getLetters().get(0), null);
    }

}
