package org.kasador.realestatecompany.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Pool<T> {

    void add(T t);

    Optional<T> find(String id);

    void remove(T t);

    List<? extends T> getAll();
}
