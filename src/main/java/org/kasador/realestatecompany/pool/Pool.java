package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.exception.PoolException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Pool<T> {

    void add(T t) throws PoolException;

    Optional<T> find(String id);

    void remove(T t);

    List<? extends T> getAll();

    void restore(List<? extends T> poolBackup);
}
