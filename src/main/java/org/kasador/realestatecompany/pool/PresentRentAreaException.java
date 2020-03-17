package org.kasador.realestatecompany.pool;

public class PresentRentAreaException extends RuntimeException {
    public PresentRentAreaException(String already_exist) {
        super(already_exist);
    }
}
