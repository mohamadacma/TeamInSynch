

package com.nashss.se.teaminsynchservice.activity.requests;

public class GetGeoCodingRequest {
    private final String city;

    public GetGeoCodingRequest(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "GetGeoCodingRequest{" +
                "city='" + city + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String city;

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public GetGeoCodingRequest build() {
            return new GetGeoCodingRequest(city);
        }
    }
}

