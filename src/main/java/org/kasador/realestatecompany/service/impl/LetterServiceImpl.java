package org.kasador.realestatecompany.service.impl;

import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.Reason;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.service.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;

public class LetterServiceImpl implements LetterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LetterServiceImpl.class);
    private static final Path letterFolder = Paths.get("letters/");

    @Override
    public void send(RentArea rentArea, Reason reason, String message) {
        if (rentArea.getTenant() != null) {
            try {
                var filePath = Files.createFile(generatePath(rentArea, reason));
                Files.writeString(filePath, message);
                rentArea.getTenant().getLetters().add(filePath.toFile());

                LOGGER.debug(
                        MessageFormat
                                .format("SEND LETTER to:{0}message: {1}",
                                        rentArea.getTenant().getId(), message));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeOldApartmentLetters(RentArea rentArea) throws IOException {
        if (rentArea.getTenant() != null) {
            Files
                    .walk(generateUserFolderPath(rentArea.getTenant()))
                    .filter(f -> f.getFileName().toString().split("_")[1].equals(rentArea.getId()))
                    .forEach(path -> {
                        try {
                            rentArea.getTenant().getLetters().remove(path.toFile());
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private Path generateUserFolderPath(Person person) {
        return Paths.get(letterFolder +
                "/" + person.getName() +
                "_" + person.getSurname() +
                "_" + person.getId());
    }

    private Path generatePath(RentArea rentArea, Reason reason) throws IOException {
        var person = rentArea.getTenant();
        var personDirectory = generateUserFolderPath(person);
        if (!Files.exists(letterFolder)) {
            Files.createDirectory(letterFolder);
        }
        if (!Files.exists(personDirectory)) {
            Files.createDirectory(personDirectory);
        }
        return Paths.get(personDirectory.toString() +
                "/" + reason.name() +
                "_" + rentArea.getId() +
                "_" + LocalDate.now() +
                ".txt");
    }
}
