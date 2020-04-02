package com.example.geoconverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GeoLocationController.class)
class GeoLocationControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private GeoLocationController geoLocationController;

  @Test
  public void shouldReturnGeoInfoObjectAsCsvResponseWithStatusOk() throws Exception {

    String csv =
        "position,key,name,fullName,country,inEurope,countryCode,coreCountry,distance,iata_airport_code,_type,_id,geo_position,location_id\n"
            + "Vancover,key,samplename,SampleName,,true,US,true,40,432,type10,1230,\"{latitude=111.123, longitude=2222.2222}\",13\n";
    when(geoLocationController.getGeoInfosCsv(1)).thenReturn(csv);
    mockMvc
        .perform(get("/geoInfo/geoInfoCsv/1").contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string(csv));
  }

  @Test
  public void shouldReturnGePositionObjectAsCsvResponseWithStatusOk() throws Exception {

    String csv = "'id, latitude, longitude'\n" + "'1230,111.123,2222.2222'\n";

    String[] params = {"_id", "latitude", "longitude"};

    when(geoLocationController.getGeoInfosFilteredCsv(1, params)).thenReturn(csv);
    mockMvc
        .perform(
            get("/geoInfo/geoInfoFilteredCsv/1")
                .param("params", params)
                .contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string(csv));
  }

  @Test
  public void ShouldReturnEmptyValueStringForGettingZeroGeoInfos() throws Exception {
    when(geoLocationController.getGeoInfosCsv(0)).thenReturn("Empty Value");
    mockMvc
        .perform(get("/geoInfo/geoInfoCsv/0").contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string("Empty Value"));
  }

  @Test
  public void ShouldReturnEmptyValueStringForGettingZeroGeoInfosWithParams() throws Exception {
    String[] params = {"_id", "latitude", "longitude"};
    when(geoLocationController.getGeoInfosFilteredCsv(0, params)).thenReturn("Empty Value");
    mockMvc
        .perform(
            get("/geoInfo/geoInfoFilteredCsv/0")
                .param("params", params)
                .contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string("Empty Value"));
  }
}
