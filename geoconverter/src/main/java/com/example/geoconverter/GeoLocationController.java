package com.example.geoconverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/geoInfo")
public class GeoLocationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CsvService csvService;

    @GetMapping(value = "/geoInfoCsv/{size}")
    @ResponseBody
    public String getGeoLocationListAsCsv(@PathVariable Integer size) {
        String json = restTemplate.getForObject("http://localhost:8086/generate/json/" + size, String.class);
        String result;
        try {
            result = csvService.readJsonAsCsv(json);
        } catch (IOException e) {
            throw new GeoInfoNotFoundException("Could not read value as csv");
        }
        return result;
    }

    @GetMapping(value = "/geoPositionCsv/{size}")
    @ResponseBody
    public String getGeoLocationWithParameters(@PathVariable Integer size, @RequestParam List<String> params) throws Exception {
        String json = restTemplate.getForObject("http://localhost:8086/generate/json/" + size, String.class);

        return csvService.getGeoPositionsAsCsv(csvService.getGeoPositions(json), params);
    }

}