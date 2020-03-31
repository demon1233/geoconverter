package com.example.geoconverter;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GeoLocationController.class)
public class CsvReaderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoLocationController geoLocationController;

    @Test
    public void shouldReturnGeoInfoObjectAsCsvResponseWithStatusOk() throws Exception {

        String csv = "position,key,name,fullName,country,inEurope,countryCode,coreCountry,distance,iata_airport_code,_type,_id,geo_position,location_id\n" +
                "Vancover,key,samplename,SampleName,,true,US,true,40,432,type10,1230,\"{latitude=111.123, longitude=2222.2222}\",13\n";
        when(geoLocationController.getGeoLocationListAsCsv(1)).thenReturn(csv);
        mockMvc.perform(get("/geoInfo/geoInfoCsv/1").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(csv));
    }

    @Test
    public void shouldReturnGePositionObjectAsCsvResponseWithStatusOk() throws Exception {

        String csv = "'id, latitude, longitude'\n" +
                "'1230,111.123,2222.2222'\n";

        List<String> params = Arrays.asList("id");
        when(geoLocationController.getGeoLocationWithParameters(1, params)).thenReturn(csv);
        mockMvc.perform(get("/geoInfo/geoPositionCsv/1").param("params", "id").contentType(MediaType.TEXT_PLAIN).content(csv)).andExpect(status().isOk());
    }
}