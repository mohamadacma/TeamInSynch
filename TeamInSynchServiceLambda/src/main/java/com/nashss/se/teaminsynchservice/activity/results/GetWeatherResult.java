
package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.WeatherModel;

public class GetWeatherResult {
    private final WeatherModel weather;

    private GetWeatherResult(WeatherModel weather) {
        this.weather = weather;
    }

    public WeatherModel getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "WeatherResult{" +
                "weather=" + weather +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WeatherModel weather;

        public Builder withWeather(WeatherModel weather) {
            this.weather = weather;
            return this;
        }

        public GetWeatherResult build() {
            return new GetWeatherResult(weather);
        }
    }
}
