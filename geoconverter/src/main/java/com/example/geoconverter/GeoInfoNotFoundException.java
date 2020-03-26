package com.example.geoconverter;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GeoInfoNotFoundException extends RuntimeException {
    public GeoInfoNotFoundException(String message) {
        super(message);
    }
}
