package com.example.geoconverter.dao;


import com.example.geoconverter.GeoPositionDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@JsonDeserialize(using = GeoPositionDeserializer.class)
public class GeoPosition {


    @JsonProperty("latitude")
    public String latitude;

    @JsonProperty("longitude")
    public String longitude;

    public String id;

}
