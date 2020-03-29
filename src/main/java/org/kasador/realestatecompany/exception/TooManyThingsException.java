package org.kasador.realestatecompany.exception;

public class TooManyThingsException extends Exception {
    public TooManyThingsException() {
        super("Delete some old items to insert a new one");
    }
}
