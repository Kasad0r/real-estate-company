package org.kasador.realestatecompany.modeling;

import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.service.LetterService;
import org.kasador.realestatecompany.domain.Reason;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class RentPaymentTask extends TimerTask {
    private RentAreaPool defaultPool = RentAreaPool.getInstance();
    private LetterService letterService;
    private RentAreaService rentAreaService;

    public RentPaymentTask(LetterService letterService, RentAreaService rentAreaService) {
        this.letterService = letterService;
        this.rentAreaService = rentAreaService;
    }

    @Override//TODO
    public void run() {
        List<? extends RentArea> collect = defaultPool.getAll().stream()
                .filter(r -> r.getRentEndDate().isAfter(GlobalTimeTask.getCurrentDate())).collect(Collectors.toList());

        collect.stream().filter(r -> r.getRentEndDate().plusDays(30).isAfter(GlobalTimeTask.getCurrentDate()))
                .forEach(r -> {
                    letterService.send(r, Reason.EVICTION_LETTER, "Expired rent date over 30 days");
                    rentAreaService.evictRentArea(r);
                });
    }
}
