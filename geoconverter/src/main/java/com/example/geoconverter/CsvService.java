package com.example.geoconverter;

import com.example.geoconverter.pojo.GeoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CsvService {

  @Autowired private ObjectMapper mapper;

  private CsvMapper csvMapper = new CsvMapper();

  public String readJsonAsCsv(String json) throws IOException {
    SimpleFilterProvider filters = new SimpleFilterProvider();
    filters.setFailOnUnknownId(false);
    mapper.setFilterProvider(filters);

    List<GeoInfo> geoInfos = getGeoInfos(json, mapper);
    String deserializeJson = mapper.writeValueAsString(geoInfos);

    JsonNode jsonTree = mapper.readTree(deserializeJson);
    CsvSchema csvSchema = getColumns(jsonTree);
    return getStringAsCsv(jsonTree, csvSchema);
  }

  public String readJsonAsCsv(String json, String[] params) throws IOException {
    FilterProvider filters =
        new SimpleFilterProvider()
            .addFilter("csvFilter", SimpleBeanPropertyFilter.filterOutAllExcept(params));
    mapper.setFilterProvider(filters);

    List<GeoInfo> geoInfos = getGeoInfos(json, mapper);
    String jsonString =
        mapper.writer(filters).withDefaultPrettyPrinter().writeValueAsString(geoInfos);
    JsonNode jsonTree = mapper.readTree(jsonString);
    CsvSchema csvSchema = getColumns(jsonTree);

    return getStringAsCsv(jsonTree, csvSchema);
  }

  private String getStringAsCsv(JsonNode jsonTree, CsvSchema csvSchema)
      throws JsonProcessingException {
    return csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValueAsString(jsonTree);
  }

  private CsvSchema getColumns(JsonNode jsonTree) {
    CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
    JsonNode firstObject = jsonTree.elements().next();
    firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

    return csvSchemaBuilder.build().withHeader();
  }

  private List<GeoInfo> getGeoInfos(String json, ObjectMapper mapper) throws IOException {
    return mapper.readValue(json, new TypeReference<List<GeoInfo>>() {});
  }
}
