
package com.nashss.se.teaminsynchservice.activity.requests;

public class GetWeatherRequest {
    private final double latitude;
    private final double longitude;
    private final String weatherCode;
    private final String daily;
    private final String maxTemperature;
    private final String minTemperature;

    private GetWeatherRequest(double latitude, double longitude, String weatherCode, String daily, String maxTemperature, String minTemperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherCode = weatherCode;
        this.daily = daily;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public String getDaily() {
        return daily;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    @Override
    public String toString() {
        return "GetWeatherRequest{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", weatherCode='" + weatherCode + '\'' +
                ", daily='" + daily + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double latitude;
        private double longitude;
        private String weatherCode;
        private String daily;
        private String maxTemperature;
        private String minTemperature;

        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withWeatherCode(String weatherCode) {
            this.weatherCode = weatherCode;
            return this;
        }

        public Builder withDaily(String daily) {
            this.daily = daily;
            return this;
        }

        public Builder withMaxTemperature(String maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public Builder withMinTemperature(String minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public GetWeatherRequest build() {
            return new GetWeatherRequest(latitude, longitude, weatherCode, daily, maxTemperature, minTemperature);
        }
    }
}

