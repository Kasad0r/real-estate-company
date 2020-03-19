package org.kasador.realestatecompany.exception;

public class PoolException extends RuntimeException {
    public PoolException(String already_exist) {
        super(already_exist);
    }
}
