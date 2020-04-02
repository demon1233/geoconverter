package com.example.geoconverter;

import com.example.geoconverter.pojo.GeoInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class GeoInfoDeserializerTest {

  @Test
  public void ShouldDeserializeGeoInfos() throws IOException {
    String json =
        "[{\"position\":\"Vancover\",\"key\":\"key\",\"name\":\"samplename\",\"fullName\":\"SampleName\",\"country\":null,\"inEurope\":true,\"countryCode\":\"US\",\"coreCountry\":true,\"distance\":40,\"iata_airport_code\":\"432\",\"_type\":\"type10\",\"_id\":\"1230\",\"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\"location_id\":13}]";

    ObjectMapper mapper = new ObjectMapper();
    List<GeoInfo> geoInfos = mapper.readValue(json, new TypeReference<List<GeoInfo>>() {});
    GeoInfo firstObject = geoInfos.get(0);

    Assertions.assertEquals("Vancover", firstObject.getPosition());
    Assertions.assertEquals("key", firstObject.getKey());
    Assertions.assertEquals("samplename", firstObject.getName());
    Assertions.assertEquals("SampleName", firstObject.getFullName());
    Assertions.assertEquals("432", firstObject.getIataAirportCode());
    Assertions.assertEquals("type10", firstObject.getType());
    Assertions.assertEquals("1230", firstObject.getId());
    Assertions.assertEquals("111.123", firstObject.getLatitude());
    Assertions.assertEquals("2222.2222", firstObject.getLongitude());
    Assertions.assertEquals("US", firstObject.getCountryCode());
    Assertions.assertEquals(false, firstObject.getCoreCountry());
    Assertions.assertEquals(false, firstObject.getInEurope());
  }
}
