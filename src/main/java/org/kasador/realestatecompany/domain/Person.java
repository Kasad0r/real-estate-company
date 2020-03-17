package org.kasador.realestatecompany.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Person {
    private String id;

    private List<Apartment> apartments;
    private List<File> billings;

    public Person() {
        billings = new ArrayList<>();
        apartments = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
