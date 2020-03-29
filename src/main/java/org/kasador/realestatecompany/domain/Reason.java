package org.kasador.realestatecompany.domain;

import java.io.Serializable;

public enum Reason implements Serializable {
    EVICTION_LETTER("evict_let_"), NON_PAY_WARN("non-payment_warning_"), CAR_SOLD("car-sold_let_");

    Reason(String res) {

    }
}
