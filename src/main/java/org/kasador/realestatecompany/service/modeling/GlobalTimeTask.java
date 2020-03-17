package org.kasador.realestatecompany.service.modeling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.TimerTask;


public class GlobalTimeTask extends TimerTask {
    private static Logger LOGGER = LoggerFactory.getLogger(GlobalTimeTask.class);
    private static LocalDate localDate = LocalDate.now();

    @Override
    public void run() {
        localDate = localDate.plusDays(1);
        LOGGER.debug("CurrentDate: " + localDate.toString());
    }

    public static LocalDate getCurrentDate() {
        return localDate;
    }
}
