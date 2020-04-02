package com.example.geoconverter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CsvServiceTest {


    @Autowired
    private CsvService csvService;

    @Test
    public void shouldConvertJsonToCsv() throws IOException {
        String json = "[{\"position\":\"Vancover\",\"key\":\"key\",\"name\":\"samplename\",\"fullName\":\"SampleName\",\"country\":null,\"inEurope\":true,\"countryCode\":\"US\",\"coreCountry\":true,\"distance\":40,\"iata_airport_code\":\"432\",\"_type\":\"type10\",\"_id\":\"1230\",\"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\"location_id\":13}]";


        String expectedResult = "position,key,name,fullName,country,inEurope,countryCode,coreCountry,distance,iata_airport_code,_type,_id,latitude,longitude,location_id\n" +
                "Vancover,key,samplename,SampleName,,false,US,false,,432,type10,1230,111.123,2222.2222,\n";
        Assertions.assertEquals(expectedResult, csvService.readJsonAsCsv(json));
    }


    @Test
    public void shouldConvertJsonToFilteredCSV() throws IOException {
        String json = "[{\"position\":\"Vancover\",\"key\":\"key\",\"name\":\"samplename\",\"fullName\":\"SampleName\",\"country\":null,\"inEurope\":true,\"countryCode\":\"US\",\"coreCountry\":true,\"distance\":40,\"iata_airport_code\":\"432\",\"_type\":\"type10\",\"_id\":\"1230\",\"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\"location_id\":13}]";


        String expectedResult = "key,_id,latitude,longitude\n" +
                "key,1230,111.123,2222.2222\n";

        String[] params = {"_id", "key", "latitude", "longitude"};
        Assertions.assertEquals(expectedResult, csvService.readJsonAsCsv(json, params));
    }

}