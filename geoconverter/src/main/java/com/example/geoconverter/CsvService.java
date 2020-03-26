package com.example.geoconverter;

import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {


    public String readJsonAsCsv(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        JsonNode jsonTree = mapper.readTree(json);
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();

        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writer(filterProvider)
                .with(csvSchema).writeValueAsString(jsonTree);
        return csvMapper.writerFor(JsonNode.class)
                .with(csvSchema).writeValueAsString(jsonTree);
    }


    public List<GeoPosition> getGeoPositions(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<GeoPosition>>() {
        });
    }

    public String getGeoPositionsAsCsv(List<GeoPosition> geoPositions, List<String> params) {


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("'");
        for (String param : params) {
            stringBuilder.append(param + ", ");
        }
        stringBuilder.append('\n');
        for (GeoPosition geoPosition : geoPositions
        ) {
            createByParam(transformQueryParamToDictionary(params), geoPosition, stringBuilder);
        }


        return stringBuilder.toString();
    }

    StringBuilder createByParam(Map<String, String> params, GeoPosition geoPosition, StringBuilder str) {
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
}



