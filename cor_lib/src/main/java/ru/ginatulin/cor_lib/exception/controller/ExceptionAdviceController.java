package ru.ginatulin.cor_lib.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ginatulin.cor_lib.exception.models.MarketError;
import ru.ginatulin.cor_lib.exception.models.NotFoundException;

@ControllerAdvice
public class ExceptionAdviceController {
    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFound(NotFoundException e){
        MarketError response = new MarketError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
