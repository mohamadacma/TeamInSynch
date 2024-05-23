package com.nashss.se.teaminsynchservice.models;

import java.util.Objects;

public class WeatherModel {
    private final String city;
    private final Double temperature;
    private final String weatherCondition;
    private final String weatherDescription;

    private WeatherModel(String city, Double temperature, String weatherCondition, String weatherDescription) {
        this.city = city;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.weatherDescription = weatherDescription;
}

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherModel that = (WeatherModel) o;
        return Objects.equals(city, that.city) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(weatherCondition, that.weatherCondition) &&
                Objects.equals(weatherDescription, that.weatherDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, temperature, weatherCondition, weatherDescription);
    }

    public static class Builder {
        private String city;
        private Double temperature;
        private String weatherCondition;
        private String weatherDescription;

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withTemperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withWeatherCondition(String weatherCondition) {
            this.weatherCondition = weatherCondition;
            return this;
        }

        public Builder withWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
            return this;
        }

        public WeatherModel build() {
            return new WeatherModel(city, temperature, weatherCondition, weatherDescription);
        }
    }
}


