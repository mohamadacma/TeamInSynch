package com.nashss.se.teaminsynchservice.dynamodb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.teaminsynchservice.activity.newsRequest.FetchNewsRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetNewsResult;
import com.nashss.se.teaminsynchservice.models.NewsModel;
import com.nashss.se.teaminsynchservice.utils.APIUtils;
import com.nashss.se.teaminsynchservice.utils.TeamInSynchServiceUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {
    private static final String API_BASE_URL = "https://api.worldnewsapi.com/search-news";
    private static final String apiKey = APIUtils.getSecret();

    @Inject
    public NewsDao() {
    }

    public GetNewsResult getNews(FetchNewsRequest request) {
        String newsApiUrl = buildNewsApiUrl(request);
        JsonNode newsResponse = makeApiCall(newsApiUrl);
        NewsModel newsModel = parseNewsResponse(newsResponse);
        return GetNewsResult.builder()
                .withNewsModel(newsModel)
                .build();
    }

    public JsonNode makeApiCall(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", apiKey);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                /*BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                 */
                // Parse response string into JsonNode
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readTree(connection.getInputStream());
            } else {
                throw new RuntimeException("Failed to get data from API. Response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to make API call", e);
        }
    }
    private String buildNewsApiUrl(FetchNewsRequest request) {
        StringBuilder urlBuilder = new StringBuilder(API_BASE_URL);
        if (TeamInSynchServiceUtils.isValidString(request.getLocationFilter())) {
            urlBuilder.append(request.getLocationFilter());
        }
            return urlBuilder.toString();

    }

    private NewsModel parseNewsResponse(JsonNode rootNode) {
        if (rootNode == null) {
            throw new RuntimeException("Received null response from API");
        }
        if (!rootNode.has("news")) {
            throw new RuntimeException("Missing 'news' field in the response");
        }
        List<String> headlines = new ArrayList<>();
        List<String> sources = new ArrayList<>();
        List<String> URLs = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<String> publishDates = new ArrayList<>();

        JsonNode newsArray = rootNode.path("news");
        if (newsArray.isArray()) {
            for (JsonNode newsItem : newsArray) {
                headlines.add(newsItem.path("title").asText());
                sources.add(newsItem.path("source_country").asText());
                URLs.add(newsItem.path("url").asText());
                images.add(newsItem.path("image").asText());
                publishDates.add(newsItem.path("publish_date").asText());
            }
        }
        return new NewsModel.Builder()
                .withHeadlines(headlines)
                .withSources(sources)
                .withURLs(URLs)
                .withImages(images)
                .withPublishDates(publishDates)
                .build();
    }
    }


