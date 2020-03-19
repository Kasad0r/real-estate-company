package org.kasador.realestatecompany.tools;

import org.junit.Test;
import org.kasador.realestatecompany.domain.RentArea;

import java.util.List;

public class StandardRestorerTest {

    @Test
    public void getRentAreas() {
        Restorer restorer = new StandardRestorer();
        List<? extends RentArea> rentAreas = restorer.getRentAreas();
        System.out.println(rentAreas.toString());
    }

    @Test
    public void getPersons() {
    }
}
