package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StandardSaver implements Saver {
    public StandardSaver() {
        try {
            Files.createDirectories(Paths.get("data/"));
            if (!Files.exists(Paths.get(Saver.personsPollPath))) {
                Files.createFile(Paths.get(Saver.personsPollPath));
            }
            if (!Files.exists(Paths.get(Saver.rentAreaPoolPath))) {
                Files.createFile(Paths.get(Saver.rentAreaPoolPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllData() {
        saveRentAreaData();
        savePersons();
    }

    @Override
    public void saveRentAreaData() {

        try (var fos = new FileOutputStream(rentAreaPoolPath);
             var oos = new ObjectOutputStream(fos)) {
            System.out.println(RentAreaPool.getInstance().getAll());
            oos.writeObject(RentAreaPool.getInstance().getAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePersons() {
        try (var fos = new FileOutputStream(personsPollPath);
             var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(PersonPool.getInstance().getAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUserViewData() {

    }

}
