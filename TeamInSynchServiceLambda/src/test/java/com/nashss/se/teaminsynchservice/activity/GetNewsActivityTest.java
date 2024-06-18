package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.newsRequest.FetchNewsRequest;
import com.nashss.se.teaminsynchservice.activity.requests.GetNewsRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.results.GetNewsResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.NewsDao;
import com.nashss.se.teaminsynchservice.dynamodb.WeatherDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import com.nashss.se.teaminsynchservice.models.NewsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetNewsActivityTest {
    @Mock
    NewsDao newsDao;
    @Mock
    MemberDao memberDao;
    @Mock
    WeatherDao weatherDao;

    private GetNewsActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new GetNewsActivity(newsDao, memberDao, weatherDao);
    }
    @Test
    public void handleRequest_withMemberId_returnsNewsResults() {
        //GIVEN
        String memberId = "123";
        String city = "Sao Paulo";
        double latitude = -23.5505;
        double longitude = -46.6333;

        Member member = new Member();
        member.setMemberId(memberId);
        member.setCity(city);

        GetGeoCodingResult geoCodingResult = GetGeoCodingResult.builder()
                .withLatitude(latitude)
                .withLongitude(longitude)
                .build();

        GetNewsRequest request = GetNewsRequest.builder()
                .withMemberId(memberId)
                .withManagerEmail("manager@example.com")
                .build();

        NewsModel newsModel = new NewsModel.Builder()
                .withHeadlines(Arrays.asList("Headline 1", "Headline 2"))
                .withSources(Arrays.asList("Source 1", "Source 2"))
                .withURLs(Arrays.asList("URL 1", "URL 2"))
                .withImages(Arrays.asList("Image 1", "Image 2"))
                .withPublishDates(Arrays.asList("Date 1", "Date 2"))
                .build();

        GetNewsResult expectedResult = GetNewsResult.builder()
                .withNewsModel(newsModel)
                .build();

        when(memberDao.getMemberById(memberId)).thenReturn(member);
        when(weatherDao.getGeoCoding(any())).thenReturn(geoCodingResult);
        when(newsDao.getNews(any(FetchNewsRequest.class))).thenReturn(expectedResult);

        //WHEN
        GetNewsResult result = activity.handleRequest(request);

        //THEN
        verify(memberDao, times(1)).getMemberById(memberId);
        verify(weatherDao,times(1)).getGeoCoding(any());
        verify(newsDao,times(1)).getNews(any(FetchNewsRequest.class));
        assertNotNull(result);
        assertEquals(newsModel,result.getNewsModel());
    }
    @Test
    public void handleRequest_getNewsWithInvalidId_throwsMemberNotFoundException(){
        //GIVEN
        String invalidMemberId = "invalidId";

        GetNewsRequest request = GetNewsRequest.builder()
                .withMemberId(invalidMemberId)
                .withManagerEmail("manager@example.com")
                .build();

        when(memberDao.getMemberById(invalidMemberId)).thenReturn(null);
        //WHEN and THEN
        assertThrows(MemberNotFoundException.class, () -> activity.handleRequest(request));
    }

}
