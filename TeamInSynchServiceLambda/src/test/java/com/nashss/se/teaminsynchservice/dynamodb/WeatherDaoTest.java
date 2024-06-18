package com.nashss.se.teaminsynchservice.dynamodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.GetGeoCodingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherDaoTest {
    @Mock
    private WeatherDao weatherDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void getGeoCoding_withValidCity_returnsGeoCodingResult() throws JsonProcessingException {
        // Given
        String city = "Sao Paulo";

        GetGeoCodingRequest request = GetGeoCodingRequest.builder()
                .withCity(city)
                .build();

        String geoCodingApiResponse = "{ \"results\": [{ \"latitude\": -23.5505, \"longitude\": -46.6333 }] }";
        JsonNode geoCodingJsonNode = new ObjectMapper().readTree(geoCodingApiResponse);

        // Mock makeApiCall to return predefined JSON response
        when(weatherDao.makeApiCall(anyString())).thenReturn(geoCodingJsonNode);

        // Mock getGeoCoding to call the real method
        doCallRealMethod().when(weatherDao).getGeoCoding(any(GetGeoCodingRequest.class));

        // WHEN
        GetGeoCodingResult result = weatherDao.getGeoCoding(request);

        // THEN
        assertNotNull(result);
        assertEquals(-23.5505, result.getLatitude());
        assertEquals(-46.6333, result.getLongitude());

        // Verify the URL passed to makeApiCall
        verify(weatherDao, times(1)).makeApiCall(anyString());
    }
}


