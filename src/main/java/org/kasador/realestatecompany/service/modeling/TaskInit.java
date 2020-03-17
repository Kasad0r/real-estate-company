package org.kasador.realestatecompany.service.modeling;

import org.kasador.realestatecompany.service.impl.BillingServiceImpl;
import org.kasador.realestatecompany.service.modeling.GlobalTimeTask;
import org.kasador.realestatecompany.service.modeling.RentPaymentTask;

import java.time.LocalDate;
import java.util.Timer;

public class TaskInit {
    public void init() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RentPaymentTask(new BillingServiceImpl()), 50, 5000);
        timer.scheduleAtFixedRate(new GlobalTimeTask(), 50, 5000);
    }
}
