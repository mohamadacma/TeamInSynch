package com.nashss.se.teaminsynchservice.models;

import java.util.List;
import java.util.Objects;

public class WeatherModel {
    private final Double latitude;
    private final Double longitude;
    private final List<String> times;
    private final List<String> weatherDescriptions;
    private final List<Double> maxTemperatures;
    private final List<Double> minTemperatures;

    private WeatherModel(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.times = builder.times;
        this.weatherDescriptions = builder.weatherDescriptions;
        this.maxTemperatures = builder.maxTemperatures;
        this.minTemperatures = builder.minTemperatures;
    }
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getWeatherDescriptions() {
        return weatherDescriptions;
    }

    public List<Double> getMaxTemperatures() {
        return maxTemperatures;
    }

    public List<Double> getMinTemperatures() {
        return minTemperatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherModel that = (WeatherModel) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(times, that.times) &&
                Objects.equals(weatherDescriptions, that.weatherDescriptions) &&
                Objects.equals(maxTemperatures, that.maxTemperatures) &&
                Objects.equals(minTemperatures, that.minTemperatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, times, weatherDescriptions, maxTemperatures, minTemperatures);
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;
        private List<String> times;
        private List<String> weatherDescriptions;
        private List<Double> maxTemperatures;
        private List<Double> minTemperatures;

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withTimes(List<String> times) {
            this.times = times;
            return this;
        }

        public Builder withWeatherDescriptions(List<String> weatherDescriptions) {
            this.weatherDescriptions = weatherDescriptions;
            return this;
        }

        public Builder withMaxTemperatures(List<Double> maxTemperatures) {
            this.maxTemperatures = maxTemperatures;
            return this;
        }

        public Builder withMinTemperatures(List<Double> minTemperatures) {
            this.minTemperatures = minTemperatures;
            return this;
        }

        public WeatherModel build() {
            return new WeatherModel(this);
        }
    }
}
