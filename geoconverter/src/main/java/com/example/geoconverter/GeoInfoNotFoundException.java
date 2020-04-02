package com.example.geoconverter;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GeoInfoNotFoundException extends RuntimeException {
    public GeoInfoNotFoundException(String message) {
        super(message);
    }

    public GeoInfoNotFoundException(String s, IOException e) {
        super(s,e);
    }
}
