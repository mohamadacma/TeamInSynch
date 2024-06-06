

package com.nashss.se.teaminsynchservice.activity.requests;

    public class GetNewsRequest {
        private final double latitude;
        private final double longitude;
        private final int radius;
        private final int number;
        private final String apiKey;

        public GetNewsRequest(double latitude, double longitude, int radius, int number, String apiKey) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.number = number;
            this.apiKey = apiKey;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getRadius() {
            return radius;
        }

        public int getNumber() {
            return number;
        }

        public String getApiKey() {
            return apiKey;
        }

        @Override
        public String toString() {
            return "GetNewsRequest{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", radius=" + radius +
                    ", number=" + number +
                    ", apiKey='" + apiKey + '\'' +
                    '}';
        }

        //CHECKSTYLE:OFF:Builder
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private double latitude;
            private double longitude;
            private int radius;
            private int number;
            private String apiKey;

            public Builder withLatitude(double latitude) {
                this.latitude = latitude;
                return this;
            }

            public Builder withLongitude(double longitude) {
                this.longitude = longitude;
                return this;
            }

            public Builder withRadius(int radius) {
                this.radius = radius;
                return this;
            }

            public Builder withNumber(int number) {
                this.number = number;
                return this;
            }

            public Builder withApiKey(String apiKey) {
                this.apiKey = apiKey;
                return this;
            }

            public GetNewsRequest build() {
                return new GetNewsRequest(latitude, longitude, radius, number, apiKey);
            }
        }
    }

