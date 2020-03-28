package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.Pool;
import org.kasador.realestatecompany.pool.RentAreaPool;

public class ContextPool {
    private Restorer restorer;

    private Pool<Person> personPool;

    private Pool<RentArea> rentAreaPool;

    public ContextPool(Restorer restorer, Pool<Person> personPool, Pool<RentArea> rentAreaPool) {
        this.restorer = restorer;
        this.personPool = personPool;
        this.rentAreaPool = rentAreaPool;
    }

    public void init() {
        personPool.restore(restorer.getPersons());
        rentAreaPool.restore(restorer.getRentAreas());
    }
}
