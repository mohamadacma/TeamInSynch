package com.nashss.se.teaminsynchservice.activity.results;

public class GetGeoCodingResult {
    private final double latitude;
    private final double longitude;

    private GetGeoCodingResult(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    @Override
    public String toString() {
        return "GeoCodingResult{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double latitude;
        private double longitude;

        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public GetGeoCodingResult build() {
            return new GetGeoCodingResult(latitude, longitude);
        }
    }
}
