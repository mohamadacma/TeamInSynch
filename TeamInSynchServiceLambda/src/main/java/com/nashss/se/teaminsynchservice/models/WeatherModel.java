package com.nashss.se.teaminsynchservice.models;

import java.util.List;
import java.util.Objects;

public class WeatherModel {
    private final Double latitude;
    private final Double longitude;
    private final String time;
    private final String weatherDescription;
    private final Double maxTemperature;
    private final Double minTemperature;

    private WeatherModel(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.time = builder.time;
        this.weatherDescription = builder.weatherDescription;
        this.maxTemperature = builder.maxTemperature;
        this.minTemperature = builder.minTemperature;
    }
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherModel that = (WeatherModel) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(time, that.time) &&
                Objects.equals(weatherDescription, that.weatherDescription) &&
                Objects.equals(maxTemperature, that.maxTemperature) &&
                Objects.equals(minTemperature, that.minTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, time, weatherDescription, maxTemperature, minTemperature);
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;
        private String time;
        private String weatherDescription;
        private double maxTemperature;
        private double minTemperature;

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withTime(String time) {
            this.time = time;
            return this;
        }

        public Builder withWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
            return this;
        }

        public Builder withMaxTemperature(Double maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public Builder withMinTemperature(Double minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public WeatherModel build() {
            return new WeatherModel(this);
        }
    }
}
