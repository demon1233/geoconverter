package com.example.geoconverter;

import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class GeoPositionDeserializer extends StdDeserializer<GeoPosition> {


    protected GeoPositionDeserializer() {
        super(GeoPosition.class);
    }

    protected GeoPositionDeserializer(Class<?> vc) {
        super(vc);
    }

    protected GeoPositionDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected GeoPositionDeserializer(StdDeserializer<?> src) {
        super(src);
    }


    @Override
    public GeoPosition deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        GeoPosition geoPosition = new GeoPosition();
        String[] child = jsonNode.path("geo_position").textValue().replace("{", "").replace("}", "").split(",");
        String[] valuePartOne = child[0].split("=");
        String[] valuePartTwo = child[1].split("=");
        geoPosition.setLatitude(valuePartOne[1]);
        geoPosition.setLongitude(valuePartTwo[1]);

        geoPosition.setId(jsonNode.get("_id").textValue());

        return geoPosition;
    }
}