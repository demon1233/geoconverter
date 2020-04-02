package com.example.geoconverter;

import com.example.geoconverter.pojo.GeoInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GeoInfoDeserializer extends StdDeserializer<GeoInfo> {


    protected GeoInfoDeserializer() {
        super(GeoInfo.class);
    }

    @Override
    public GeoInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        GeoInfo geoInfo = new GeoInfo();
        geoInfo.setPosition(jsonNode.get("position").textValue());
        geoInfo.setKey(jsonNode.get("key").textValue());
        geoInfo.setName(jsonNode.get("name").textValue());
        geoInfo.setFullName(jsonNode.get("fullName").textValue());
        geoInfo.setIataAirportCode(jsonNode.get("iata_airport_code").textValue());
        geoInfo.setType(jsonNode.get("_type").textValue());
        geoInfo.setId(jsonNode.get("_id").textValue());
        String[] child = jsonNode.path("geo_position").textValue().replace("{", "").replace("}", "").split(",");
        String[] valuePartLatitude = child[0].split("=");
        String[] valueLongitude = child[1].split("=");
        geoInfo.setLatitude(valuePartLatitude[1]);
        geoInfo.setLongitude(valueLongitude[1]);
        geoInfo.setCountry(jsonNode.get("country").textValue());
        geoInfo.setLocationId(jsonNode.get("location_id").textValue());
        geoInfo.setInEurope(Boolean.valueOf(jsonNode.get("inEurope").textValue()));
        geoInfo.setCountryCode(jsonNode.get("countryCode").textValue());
        geoInfo.setCoreCountry(Boolean.valueOf(jsonNode.get("countryCode").textValue()));
        geoInfo.setDistance(jsonNode.get("distance").textValue());
        return geoInfo;
    }
}
