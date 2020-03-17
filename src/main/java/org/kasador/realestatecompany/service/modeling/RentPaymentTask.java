package org.kasador.realestatecompany.service.modeling;

import org.kasador.realestatecompany.pool.DefaultPoolImpl;
import org.kasador.realestatecompany.pool.Pool;
import org.kasador.realestatecompany.service.BillingService;

import java.util.TimerTask;

public class RentPaymentTask extends TimerTask {
    private Pool defaultPool = DefaultPoolImpl.getInstance();
    private BillingService billingService;

    public RentPaymentTask(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    public void run() {
        defaultPool.getRentAreas().stream()
                .filter(r -> r.getRentEndDate().equals(GlobalTimeTask.getCurrentDate()))
                .forEach(r -> billingService.sendBilling(r.getTenant(), "Expired rent date"));
    }
}
