package com.example.geoconverter;

import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GeoPositionDeserializerTest {


    @Test
    public void shouldCreateGeoPositionObject() throws IOException {
        String json = "{\n" +
                "  \"iata_airport_code\" : \"432\",\n" +
                "  \"_type\" : \"type12\",\n" +
                "  \"_id\" : \"1232\",\n" +
                "  \"geo_position\" : \"{latitude=113.123, longitude=2224.2222}\",\n" +
                "  \"location_id\" : \"13.0\"\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();

        GeoPosition geoPosition = mapper.readValue(json, GeoPosition.class);

        Assertions.assertEquals("113.123", geoPosition.getLatitude());
        Assertions.assertEquals("2224.2222", geoPosition.getLongitude());
        Assertions.assertEquals("1232", geoPosition.getId());
    }
}