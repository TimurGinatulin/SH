package ru.ginatulin.cor_lib.exception.models;

public class Forbidden extends RuntimeException {
    public Forbidden(String message) {
        super(message);
    }
}
