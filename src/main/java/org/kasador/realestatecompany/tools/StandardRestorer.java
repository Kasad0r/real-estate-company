package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StandardRestorer implements Restorer {
    @Override
    public List<? extends RentArea> getRentAreas() {
        try {
            if (Files.size(Paths.get(Saver.personsPollPath)) > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Saver.rentAreaPoolPath));
                ArrayList<RentArea> rentAreas = (ArrayList<RentArea>) ois.readObject();
                return rentAreas;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new NullPointerException("Cant deserialize");
        }
    }

    @Override
    public List<Person> getPersons() {


        try {
            if (Files.size(Paths.get(Saver.personsPollPath)) > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Saver.personsPollPath));
                List<Person> people = (List<Person>) ois.readObject();
                ois.close();
                return people;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new NullPointerException("Cant deserialize");
        }
    }
}
