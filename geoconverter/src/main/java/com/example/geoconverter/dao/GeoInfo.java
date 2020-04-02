package com.example.geoconverter.dao;

import com.example.geoconverter.GeoInfoDeserializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonFilter("csvFilter")
@RequiredArgsConstructor
@JsonDeserialize(using = GeoInfoDeserializer.class)
public class GeoInfo {


    private String position;


    private String key;

    private String name;

    private String fullName;

    @JsonProperty("iata_airport_code")
    private String iataAirportCode;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;

    private String country;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("location_id")
    private String locationId;

    private Boolean inEurope;

    private String countryCode;

    private Boolean coreCountry;

    private String distance;

}
