package com.nashss.se.teaminsynchservice.converters;

import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import com.nashss.se.teaminsynchservice.models.NewsModel;
import com.nashss.se.teaminsynchservice.models.WeatherModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
     * Converts between Data and API models.
     */
    public class ModelConverter {
    /**
     * Static map to hold weather code descriptions.
     */
    private final Map<Integer, String> weatherCodeDescriptions;

    public ModelConverter() {
        weatherCodeDescriptions = new HashMap<>();
        weatherCodeDescriptions.put(0, "Clear sky");
        weatherCodeDescriptions.put(1, "Mainly clear");
        weatherCodeDescriptions.put(2, "Partly cloudy");
        weatherCodeDescriptions.put(3, "Overcast");
        weatherCodeDescriptions.put(45, "Fog");
        weatherCodeDescriptions.put(48, "Depositing rime fog");
        weatherCodeDescriptions.put(51, "Drizzle: Light intensity");
        weatherCodeDescriptions.put(53, "Drizzle: Moderate intensity");
        weatherCodeDescriptions.put(55, "Drizzle: Dense intensity");
        weatherCodeDescriptions.put(56, "Freezing Drizzle: Light intensity");
        weatherCodeDescriptions.put(57, "Freezing Drizzle: Dense intensity");
        weatherCodeDescriptions.put(61, "Rain: Slight intensity");
        weatherCodeDescriptions.put(63, "Rain: Moderate intensity");
        weatherCodeDescriptions.put(65, "Rain: Heavy intensity");
        weatherCodeDescriptions.put(66, "Freezing Rain: Light intensity");
        weatherCodeDescriptions.put(67, "Freezing Rain: Heavy intensity");
        weatherCodeDescriptions.put(71, "Snow fall: Slight intensity");
        weatherCodeDescriptions.put(73, "Snow fall: Moderate intensity");
        weatherCodeDescriptions.put(75, "Snow fall: Heavy intensity");
        weatherCodeDescriptions.put(77, "Snow grains");
        weatherCodeDescriptions.put(80, "Rain showers: Slight intensity");
        weatherCodeDescriptions.put(81, "Rain showers: Moderate intensity");
        weatherCodeDescriptions.put(82, "Rain showers: Violent intensity");
        weatherCodeDescriptions.put(85, "Snow showers: Slight intensity");
        weatherCodeDescriptions.put(86, "Snow showers: Heavy intensity");
        weatherCodeDescriptions.put(95, "Thunderstorm: Slight or moderate");
        weatherCodeDescriptions.put(96, "Thunderstorm with slight hail");
        weatherCodeDescriptions.put(99, "Thunderstorm with heavy hail");
    }
    /**
     * Method to get the description for a given weather code.
     *
     * @param weatherCode the weather code for which the description is to be retrieved.
     * @return the description corresponding to the weather code, or "Unavailable weather code" if the code is not found.
     */
    public String getDescriptionFromWeatherCode(int weatherCode) {
        return weatherCodeDescriptions.getOrDefault(weatherCode, "unavailable weather code");
    }
    /**
     * Convert weather codes to descriptions.
     *
     * @param weatherCodes list of weather codes to be converted.
     * @return list of weather descriptions.
     */
    private List<String> convertWeatherCodesToDescriptions(List<Integer> weatherCodes) {
        return weatherCodes.stream()
                .map(this::getDescriptionFromWeatherCode)
                .collect(Collectors.toList());
    }
    /**
     * Converts raw weather data into a {@link WeatherModel} representation.
     *
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param times a list of times corresponding to the weather data.
     * @param weatherCodes a list of weather codes corresponding to the weather data.
     * @param maxTemperatures a list of maximum temperatures for each time.
     * @param minTemperatures a list of minimum temperatures for each time.
     * @return the converted {@link WeatherModel} containing weather descriptions and temperatures.
     */
    public WeatherModel toWeatherModel(Double latitude, Double longitude, List<String> times, List<Integer> weatherCodes, List<Double> maxTemperatures, List<Double> minTemperatures) {
        List<String> weatherDescriptions = convertWeatherCodesToDescriptions(weatherCodes);
        return new WeatherModel.Builder()
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withTimes(times)
                .withWeatherDescriptions(weatherDescriptions)
                .withMaxTemperatures(maxTemperatures)
                .withMinTemperatures(minTemperatures)
                .build();
    }
    /**
     * Converts a list of Members to a list of MemberModels.
     *
     * @param members The Members to convert to MemberModels
     * @return The converted list of MemberModels
     */
    public List<MemberModel> toMemberModelList(List<Member> members) {
        List<MemberModel> memberModels = new ArrayList<>();

        for (Member member : members) {
            memberModels.add(toMemberModel(member));
        }

        return memberModels;
    }

    /**
     * Converts a provided {@link Member} into a {@link MemberModel} representation.
     *
     * @param member the member to convert
     * @return the converted member model
     */
    public MemberModel toMemberModel(Member member) {
        return MemberModel.builder()
                .withMemberId(member.getMemberId())
                .withMemberName(member.getMemberName())
                .withMemberEmail(member.getMemberEmail())
                .withPhoneNumber(member.getPhoneNumber())
                .withCity(member.getCity())
                .withJoinDate(member.getJoinDate())
                .withBackground(member.getBackground())
                .withRole(member.getRole())
                .build();
    }

}

