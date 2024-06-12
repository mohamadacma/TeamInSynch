package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.GetWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.results.GetWeatherResult;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.FetchWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.GetGeoCodingRequest;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.WeatherDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import com.nashss.se.teaminsynchservice.models.WeatherModel;

import javax.inject.Inject;

/**
 * Implementation of the GetWeatherActivity for the TeamInSynchService's GetWeather API.
 * <p>
 * This API allows the customer to get weather details at member's location.
 */
public class GetWeatherActivity {
    private final WeatherDao weatherDao;
    private final MemberDao memberDao;

    /**
     * Instantiates a new GetMemberActivity object.
     *
     * @param memberDao MemberDao to access the members table.
     */
    @Inject
    public GetWeatherActivity(WeatherDao weatherDao, MemberDao memberDao) {

        this.weatherDao = weatherDao;
        this.memberDao = memberDao;
    }

    /**
     * This method handles the incoming request to retrieve a member's weather details
     * using the provided member ID.
     * <p>
     * If the member is found, it fetches the geocoding data using the city from the member's information,
     * then fetches the weather data using the geocoding data, and returns it wrapped in a {@link GetWeatherResult}.
     * <p>
     * If the member is not found, it throws a {@link MemberNotFoundException}.
     *
     * @param getWeatherRequest request object containing the member ID
     * @return getWeatherResult result object containing the API defined {@link WeatherModel}
     */
    public GetWeatherResult handleRequest(final GetWeatherRequest getWeatherRequest) {
        //fetch member details
        Member member = memberDao.getMemberById(getWeatherRequest.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + getWeatherRequest.getMemberId());
        }
        //get city from member
        String city = member.getCity();

        //fetch longitude and latitude
        GetGeoCodingRequest geoCodingRequest = GetGeoCodingRequest.builder()
                .withCity(city)
                .build();
        GetGeoCodingResult geoCodingResult = weatherDao.getGeoCoding(geoCodingRequest);

        // Fetch weather data using geocoding data
        FetchWeatherRequest fetchWeatherRequest = FetchWeatherRequest.builder()
                .withLatitude(geoCodingResult.getLatitude())
                .withLongitude(geoCodingResult.getLongitude())
                .withDaily("&daily=weather_code,temperature_2m_max,temperature_2m_min")
                .build();

        // Return the weather result
        return weatherDao.getWeather(fetchWeatherRequest);
    }
}


