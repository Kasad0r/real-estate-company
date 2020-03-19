package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StandardRestorer implements Restorer {
    @Override
    public List<? extends RentArea> getRentAreas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Saver.rentAreaPoolPath));) {
            ArrayList<RentArea> rentAreas = (ArrayList<RentArea>) ois.readObject();
            return rentAreas;
        } catch (IOException | ClassNotFoundException e) {
            throw new NullPointerException("Cant deserialize");
        }
    }

    @Override
    public List<Person> getPersons() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Saver.personsPollPath));) {
            List<Person> people = (List<Person>) ois.readObject();
            return people;
        } catch (IOException | ClassNotFoundException e) {
            throw new NullPointerException("Cant deserialize");
        }
    }
}
