package org.kasador.realestatecompany.tools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class AddressMockGenerator {
    //TODO
    private Random random = new Random();
    private static final int NUM_LETTERS = 26;
    private static final int ASCII_BASE = 65;
    private static final int NUM_HOUSES_IN_STREET = 99;
    private static final int POST_CODE_AREA_SIZE = 98;
    private static final int POST_CODE_LOCATION_SIZE = 9;


    private List<String> streetSuffixes;
    private List<String> cityNames;
    private List<String> districtNames;

    public AddressMockGenerator() {
        try {
            this.streetSuffixes = readLinesOfFileFromResource("streetsuffixes.txt");
            this.cityNames = readLinesOfFileFromResource("citynames.txt");
            this.districtNames = readLinesOfFileFromResource("countynames.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<String> readLinesOfFileFromResource(final String resourceName) throws IOException {
        var path = Paths.get("src/main/resources/" + resourceName);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public String generateAddress() {
        return "" + random.nextInt(NUM_HOUSES_IN_STREET) + 1 + " " + randomFromList(streetSuffixes) + ", " + randomFromList(cityNames) + ", " + randomFromList(districtNames);
    }

    private String generateRandomPostCode() {
        return String.valueOf((char) (ASCII_BASE + random.nextInt(NUM_LETTERS))) +
                (char) (ASCII_BASE + random.nextInt(NUM_LETTERS)) +
                (random.nextInt(POST_CODE_AREA_SIZE) + 1) +
                " " +
                (random.nextInt(POST_CODE_LOCATION_SIZE) + 1) +
                (char) (ASCII_BASE + random.nextInt(NUM_LETTERS)) +
                (char) (ASCII_BASE + random.nextInt(NUM_LETTERS));
    }

    private <T> T randomFromList(final List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
