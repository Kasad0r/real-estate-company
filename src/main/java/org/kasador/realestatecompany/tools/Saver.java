package org.kasador.realestatecompany.tools;

public interface Saver {
    String rentAreaPoolPath = "data/rentdata";
    String personsPollPath = "data/persons";
    void saveAllData();

    void saveRentAreaData();

    void savePersons();

    void saveUserViewData();

}