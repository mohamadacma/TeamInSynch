
package com.nashss.se.teaminsynchservice.activity.weatherRequest;

public class FetchWeatherRequest {
    private final double latitude;
    private final double longitude;
    private final String daily;

    private FetchWeatherRequest(double latitude, double longitude, String daily) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.daily = daily;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDaily() {
        return daily;
    }

    @Override
    public String toString() {
        return "FetchWeatherRequest{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", daily='" + daily + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double latitude;
        private double longitude;
        private String daily;


        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withDaily(String daily) {
            this.daily = daily;
            return this;
        }
        public FetchWeatherRequest build() {
            return new FetchWeatherRequest(latitude, longitude, daily);
        }
    }
}

