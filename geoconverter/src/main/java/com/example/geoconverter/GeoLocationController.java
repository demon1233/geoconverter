package com.example.geoconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequestMapping("/geoInfo")
public class GeoLocationController {

  @Autowired private RestTemplate restTemplate;

  @Autowired private CsvService csvService;

  @GetMapping(value = "/geoInfoCsv/{size}")
  @ResponseBody
  public String getGeoInfosCsv(@PathVariable Integer size) {
    String json =
        restTemplate.getForObject("http://localhost:8086/generate/json/" + size, String.class);

    String result;
    try {
      if ((json != null && json.equals("[]")) || json.isEmpty()) {
        result = "No Value";
      } else {
        result = csvService.readJsonAsCsv(json);
      }

    } catch (IOException e) {
      throw new GeoInfoNotFoundException("Could not read value as csv", e);
    }
    return result;
  }

  @GetMapping(value = "/geoInfoFilteredCsv/{size}")
  @ResponseBody
  public String getGeoInfosFilteredCsv(@PathVariable Integer size, @RequestParam String[] params)
      throws Exception {
    String json =
        restTemplate.getForObject("http://localhost:8086/generate/json/" + size, String.class);

    String result;
    if ((json != null && json.equals("[]")) || json.isEmpty()) {
      result = "No Value";
    } else {
      result = csvService.readJsonAsCsv(json, params);
    }
    return result;
  }
}
