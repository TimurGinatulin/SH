package ru.ginatulin.cor_lib.exception.models;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
