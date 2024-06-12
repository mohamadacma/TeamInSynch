package com.nashss.se.teaminsynchservice.dynamodb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.teaminsynchservice.activity.results.GetGeoCodingResult;
import com.nashss.se.teaminsynchservice.activity.results.GetWeatherResult;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.FetchWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.weatherRequest.GetGeoCodingRequest;
import com.nashss.se.teaminsynchservice.converters.ModelConverter;
import com.nashss.se.teaminsynchservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.teaminsynchservice.models.WeatherModel;
import com.nashss.se.teaminsynchservice.utils.TeamInSynchServiceUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDao {
    private static final String GEOCODING_API_BASE_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_API_BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final String DAILY = "weather_code,temperature_2m_max,temperature_2m_min";
    @Inject
    public WeatherDao () {};

    /**
     * Retrieves the geocoding information for a given city.
     * @param coordinatesReq The request containing the city name.
     * @return A GetGeoCodingResult containing the latitude and longitude of the city.
     */
    public GetGeoCodingResult getGeoCoding(GetGeoCodingRequest coordinatesReq) {
        //validate the city
        if(!TeamInSynchServiceUtils.isValidString(coordinatesReq.getCity())) {
            throw new InvalidAttributeValueException("enter a valid city name, please");
        }
        String geoApiUrl = buildGeoCodingApiUrl(coordinatesReq);
        //make the first call to the weatherAPI to get coordinates
        JsonNode geoResponse = makeApiCall(geoApiUrl);
        //declare an array of double that holds two values for longitude and latitude.
        double[] latLong = parseGeoCodingResponse(geoResponse);
        return GetGeoCodingResult.builder()
                .withLatitude(latLong[0])
                .withLongitude(latLong[1])
                .build();
    }

    /**
     * Retrieves the weather information for the given coordinates and parameters.
     * @param request The request containing the weather query parameters.
     * @return A GetWeatherResult containing the weather information.
     */
    public GetWeatherResult getWeather(FetchWeatherRequest request) {
        String weatherApiUrl = buildWeatherApiUrl(request);
        JsonNode weatherResponse = makeApiCall(weatherApiUrl);
        WeatherModel weatherModel = parseWeatherResponse(weatherResponse);
        return GetWeatherResult.builder()
                .withWeather(weatherModel)
                .build();
    }

    /**
     * Makes an API call to the given URL and returns the response as a JsonNode.
     * @param apiUrl The URL to call.
     * @return The JSON response as a JsonNode.
     */
    public JsonNode makeApiCall(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                // De-serialize Json string response to Json node (java object)
                return mapper.readTree(connection.getInputStream());
            }
                throw new RuntimeException("Failed to get data from API");
            } catch (IOException e) {
                throw new RuntimeException("Failed to make API call", e);
            }
    }

    /**
     * Parses the geocoding API response to extract the latitude and longitude.
     * @param rootNode The root node of the JSON response.
     * @return An array containing the latitude and longitude.
     */
    private double[] parseGeoCodingResponse(JsonNode rootNode) {
        try {
            // Check if the "results" array is present and not empty
            if (!rootNode.has("results") || rootNode.path("results").isEmpty()) {
                throw new RuntimeException("No results found in the geocoding response");
            }
            //traverse Json node tree and read the first result of the results array
            JsonNode firstResult = rootNode.path("results").get(0);
            // Check if the "latitude" and "longitude" fields are present and valid
            if (!firstResult.has("latitude") || !firstResult.has("longitude")) {
                throw new RuntimeException("Latitude or longitude field is missing in the geocoding response");
            }
            //read the latitude value using .path and convert it to double
            double latitude = firstResult.path("latitude").asDouble();
            //read longitude value
            double longitude = firstResult.path("longitude").asDouble();

            return new double[]{latitude, longitude};

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse geocoding response", e);
        }
    }

    /**
     * Builds the GeoCoding API URL with the given request parameters.
     * @param request The geocoding request containing the city name.
     * @return The constructed URL as a string.
     */
    private String buildGeoCodingApiUrl(GetGeoCodingRequest request) {
        if (!TeamInSynchServiceUtils.isValidString(request.getCity())) {
            throw new IllegalArgumentException("Invalid city parameter");
        }
        return GEOCODING_API_BASE_URL + "?name=" + request.getCity();
    }

    /**
     * Builds the Weather API URL with the given request parameters.
     * @param request The weather request containing the query parameters.
     * @return The constructed URL as a string.
     */
    private String buildWeatherApiUrl(FetchWeatherRequest request) {
        StringBuilder urlBuilder = new StringBuilder(WEATHER_API_BASE_URL);
        if (TeamInSynchServiceUtils.isValidString(Double.toString(request.getLatitude()))) {
            urlBuilder.append("?latitude=").append(request.getLatitude());
        } else {
            throw new IllegalArgumentException("Invalid latitude parameter");
        }
        if (TeamInSynchServiceUtils.isValidString(Double.toString(request.getLongitude()))) {
            urlBuilder.append("&longitude=").append(request.getLongitude());
        } else {
            throw new IllegalArgumentException("Invalid longitude parameter");
        }
       urlBuilder.append("&daily=weather_code,temperature_2m_max,temperature_2m_min");
        return urlBuilder.toString();
    }

    /**
     * Parses the weather API response to extract the weather information.
     * @param rootNode The root node of the JSON response.
     * @return A WeatherModel containing the parsed weather information.
     */
    private WeatherModel parseWeatherResponse(JsonNode rootNode) {
        try {
            //extract 1st day data of the default 7-day forecast
            String time = rootNode.path("daily").path("time").get(0).asText();
            int weatherCode = rootNode.path("daily").path("weather_code").get(0).asInt();
            String weatherDescription = new ModelConverter().getDescriptionFromWeatherCode(weatherCode);
            double maxTemperature = rootNode.path("daily").path("temperature_2m_max").get(0).asDouble();
            double minTemperature = rootNode.path("daily").path("temperature_2m_min").get(0).asDouble();

            return new WeatherModel.Builder()
                    .withTime(time)
                    .withWeatherDescription(weatherDescription)
                    .withMaxTemperature(maxTemperature)
                    .withMinTemperature(minTemperature)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather response", e);
        }
    }


}
