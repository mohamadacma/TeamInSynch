package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.newsRequest.FetchNewsRequest;
import com.nashss.se.teaminsynchservice.activity.requests.GetNewsRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.results.GetNewsResult;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.GetGeoCodingRequest;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.NewsDao;
import com.nashss.se.teaminsynchservice.dynamodb.WeatherDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import com.nashss.se.teaminsynchservice.utils.APIUtils;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the GetNewsActivity for the TeamInSynchService's GetNews API.
 * <p>
 * This API allows the customer to get news details based on the member's location.
 */
public class GetNewsActivity {
    private final Logger log = LogManager.getLogger();
    private final NewsDao newsDao;
    private final MemberDao memberDao;
    private final WeatherDao weatherDao;

    private OkHttpClient client = new OkHttpClient();

    /**
     * Instantiates a new GetNewsActivity object.
     *
     * @param newsDao NewsDao to access the news data.
     * @param memberDao MemberDao to access the members table.
     * @param weatherDao WeatherDao to access the weather data.
     */
    @Inject
    public GetNewsActivity(NewsDao newsDao, MemberDao memberDao, WeatherDao weatherDao) {

        this.newsDao = newsDao;
        this.memberDao = memberDao;
        this. weatherDao = weatherDao;
    }

    /**
     * This method handles the incoming request to retrieve a member's news details
     * using the provided member ID.
     * <p>
     * If the member is found, it fetches the geocoding data using the city from the member's information,
     * then fetches the news data using the geocoding data, and returns it wrapped in a {@link GetNewsResult}.
     * <p>
     * If the member is not found, it throws a {@link MemberNotFoundException}.
     *
     * @param getNewsRequest request object containing the member ID
     * @return getNewsResult result object containing the API defined {@link NewsModel}
     */
    public GetNewsResult handleRequest(final GetNewsRequest getNewsRequest) {
        log.info("Received GetNewsRequest {}", getNewsRequest);
        //fetch member details
        Member member = memberDao.getMemberById(getNewsRequest.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + getNewsRequest.getMemberId());
        }
        //get city from member
        String city = member.getCity();

        //fetch longitude and latitude
        GetGeoCodingRequest geoCodingRequest = GetGeoCodingRequest.builder()
                .withCity(city)
                .build();
        GetGeoCodingResult geoCodingResult = weatherDao.getGeoCoding(geoCodingRequest);
       //retrieve apiKey using secret manager
        String apiKey = APIUtils.getSecret();
        // Build location filter
        String locationFilter = String.format("?location-filter=%f,%f,20",
                geoCodingResult.getLatitude(),
                geoCodingResult.getLongitude());

        // Fetch news data using geocoding data
        FetchNewsRequest fetchNewsRequest = FetchNewsRequest.builder()
                .withLocationFilter(locationFilter)
                .withApiKey(apiKey)
                .build();

        // Return the weather result
        return newsDao.getNews(fetchNewsRequest);
    }
}




