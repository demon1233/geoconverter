package com.example.geoconverter;

import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    @Autowired
    private ObjectMapper mapper;

    public String readJsonAsCsv(String json) throws IOException {

        JsonNode jsonTree = mapper.readTree(json);
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();

        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        return csvMapper.writerFor(JsonNode.class)
                .with(csvSchema).writeValueAsString(jsonTree);
    }




    public String getGeoPositionsAsCsv(String json, List<String> params) throws IOException {

        List<GeoPosition> geoPositions = getGeoPositions(json);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("'");
        for (String param : params) {
            stringBuilder.append(param + ", ");
        }
        stringBuilder.append('\n');
        for (GeoPosition geoPosition : geoPositions
        ) {
            createGeoPointAsCsvRecordByParam(transformQueryParamToDictionary(params), geoPosition, stringBuilder);
        }


        return geoPositions.isEmpty() || params.isEmpty() ? "Empty string" : stringBuilder.toString();
    }

    StringBuilder createGeoPointAsCsvRecordByParam(Map<String, String> params, GeoPosition geoPosition, StringBuilder str) {
        str.append("'");

        if (StringUtils.isNotBlank(params.get("id"))) {
            if (geoPosition.toString().contains(params.get("id"))) {
                str.append(geoPosition.getId());
            }
        }
        if (StringUtils.isNotBlank(params.get("latitude"))) {
            if (geoPosition.toString().contains(params.get("latitude"))) {
                str.append(',').append(geoPosition.getLatitude());
            }
        }
        if (StringUtils.isNotBlank(params.get("longitude"))) {
            if (geoPosition.toString().contains(params.get("longitude"))) {
                str.append(',').append(geoPosition.getLongitude());
            }
        }
        str.append("'").append("\n");
        return str;
    }

    private Map<String, String> transformQueryParamToDictionary(List<String> params) {
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            map.put(param, param);

        }
        return map;

    }

    private List<GeoPosition> getGeoPositions(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<GeoPosition>>() {
        });
    }
}



