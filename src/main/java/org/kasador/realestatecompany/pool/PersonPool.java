package org.kasador.realestatecompany.pool;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.exception.PoolException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonPool implements Pool<Person>, Serializable {
    private static final PersonPool personPool = new PersonPool();

    private static final List<Person> persons = new ArrayList<>();

    private PersonPool() {

    }

    public static PersonPool getInstance() {
        return personPool;
    }

    @Override
    public void add(Person person) {
        if (find(person.getId()).isEmpty()) {
            persons.add(person);
        } else {
            throw new PoolException("Person already exist "+ person);
        }
    }

    @Override
    public Optional<Person> find(String id) {
        return persons.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public void remove(Person person) {
        persons.remove(person);
    }

    @Override
    public List<? extends Person> getAll() {
        return persons;
    }
}
