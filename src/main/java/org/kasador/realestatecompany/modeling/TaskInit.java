package org.kasador.realestatecompany.modeling;

import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.service.impl.LetterServiceImpl;

import java.util.Timer;

public class TaskInit {

    private RentAreaService rentAreaService;

    public TaskInit(RentAreaService rentAreaService) {
        this.rentAreaService = rentAreaService;
    }

    public void init() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RentPaymentTask(new LetterServiceImpl(), rentAreaService), 50, 5000);
        timer.scheduleAtFixedRate(new GlobalTimeTask(), 50, 5000);
    }
}
