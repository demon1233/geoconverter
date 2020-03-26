package com.example.geoconverter.dao;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoInfo {

    @JsonFilter("myFilter")
    private String position;

    @JsonProperty("key")
    private String key;

    @JsonFilter("myFilter")
    private String name;

    private String fullName;

    @JsonProperty("iata_airport_code")
    private String iataAirportCode;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;

    private String country;
    @JsonProperty("geo_position")
    private String geoPosition;
    @JsonProperty("location_id")
    private String locationId;

    private Boolean inEurope;

    private String countryCode;

    private Boolean coreCountry;

    private String distance;

}
