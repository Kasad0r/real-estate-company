package org.kasador.realestatecompany.tools;


import org.junit.jupiter.api.Test;
import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.pool.RentAreaPool;

import java.time.LocalDate;

public class StandardSaverTest {
    private RentAreaPool rentAreaPool;

    @Test
    public void saveRentAreaData() {
        rentAreaPool = RentAreaPool.getInstance();
        rentAreaPool.add(new Apartment(LocalDate.now(), LocalDate.now().plusDays(30), 30.0));
        Saver saver = new StandardSaver();
        saver.saveRentAreaData();
    }


}
