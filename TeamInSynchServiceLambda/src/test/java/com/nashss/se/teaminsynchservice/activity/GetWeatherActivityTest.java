/* package com.nashss.se.teaminsynchservice.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.teaminsynchservice.activity.requests.GetWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.results.GetWeatherResult;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.FetchWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.GetGeoCodingRequest;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.WeatherDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.WeatherModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


public class GetWeatherActivityTest {
    @Mock
    MemberDao memberDao;
    @Mock
    WeatherDao weatherDao;

    private GetWeatherActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new GetWeatherActivity(weatherDao, memberDao);
    }
    @Test
    public void handleRequest_withValidUrls_returnsResponse() {
        //GIVEN
        String memberId = "123";
        String memberName = "Ip Man";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "Sao Paulo";
        String background = "Juggler";
        String role = "Tea maker";
        String memberEmail = "john.Claude@example.com";
        String teamName = "Alpha";

        Member existingMember = new Member();
        existingMember.setMemberId(memberId);
        existingMember.setMemberName(memberName);
        existingMember.setJoinDate(joinDate);
        existingMember.setPhoneNumber(phoneNumber);
        existingMember.setCity(city);
        existingMember.setBackground(background);
        existingMember.setRole(role);
        existingMember.setMemberEmail(memberEmail);
        existingMember.setMemberId(memberId);
        existingMember.setTeamName(teamName);
        String managerEmail = "testManagerEmail";

        double latitude = 37.7749;
        double longitude = -122.4194;
        String daily = "weathercode,temperature_2m_max,temperature_2m_min";

        GetWeatherRequest request = new GetWeatherRequest(memberId, managerEmail);

        WeatherModel testWeather = new WeatherModel.Builder()
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withTime("2024-06-10T12:00:00Z")
                .withWeatherDescription("Sunny")
                .withMaxTemperature(25.0)
                .withMinTemperature(15.0)
                .build();


        GetGeoCodingRequest geoCodingRequest = GetGeoCodingRequest.builder()
                .withCity(city)
                .build();
        FetchWeatherRequest fetchWeatherRequest = FetchWeatherRequest.builder()
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withDaily(daily)
                .build();

        when(memberDao.getMemberById(memberId)).thenReturn(existingMember);
        when(weatherDao.getGeoCoding(geoCodingRequest))
                .thenReturn(GetGeoCodingResult.builder().
                        withLatitude(latitude)
                        .withLongitude(longitude)
                        .build());

        when(weatherDao.getWeather(fetchWeatherRequest))
                .thenReturn(GetWeatherResult.builder()
                       .withWeather(testWeather)
                        .build());

        //WHEN
        GetWeatherResult result = activity.handleRequest(request);
        //THEN
        assertNotNull(result);
        verify(memberDao).getMemberById(memberId);
        verify(weatherDao).getGeoCoding(any(GetGeoCodingRequest.class));
        verify(weatherDao).getWeather(any(FetchWeatherRequest.class));
        assertEquals(testWeather, result.getWeather());

    }
    @Test
    public void GetGeoCoding_withValidCity_returnsCoordinates() throws IOException {
        // GIVEN
        String city = "Berlin";
        GetGeoCodingRequest request = new GetGeoCodingRequest(city);

        String jsonResponse = "{ \"results\": [{ \"latitude\": 52.5200, \"longitude\": 13.4050 }] }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedResponse = mapper.readTree(jsonResponse);



        // WHEN
        WeatherDao weatherDao1 = new WeatherDao();
        GetGeoCodingResult result = weatherDao1.getGeoCoding(request);

        // THEN
        assertNotNull(result);
        assertEquals(52.5200, result.getLatitude());
        assertEquals(13.4050, result.getLongitude());
    }
}
*/