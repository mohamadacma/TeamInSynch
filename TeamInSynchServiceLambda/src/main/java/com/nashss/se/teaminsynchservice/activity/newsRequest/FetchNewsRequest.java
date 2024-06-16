

package com.nashss.se.teaminsynchservice.activity.newsRequest;

    public class FetchNewsRequest {
        private final String locationFilter;
        private final String apiKey;

        public FetchNewsRequest(String locationFilter, String apiKey) {
            this.locationFilter = locationFilter;
            this.apiKey = apiKey;
        }

        public String getLocationFilter() {
            return locationFilter;
        }
        public String getApiKey() {
            return apiKey;
        }

        @Override
        public String toString() {
            return "GetNewsRequest{" +
                    "locationFilter=" + locationFilter +
                    ", apiKey='" + apiKey + '\'' +
                    '}';
        }

        //CHECKSTYLE:OFF:Builder
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {

            private String locationFilter;
            private String apiKey;

            public Builder withLocationFilter(String locationFilter) {
                this.locationFilter = locationFilter;
                return this;
            }

            public Builder withApiKey(String apiKey) {
                this.apiKey = apiKey;
                return this;
            }

            public FetchNewsRequest build() {
                return new FetchNewsRequest(locationFilter, apiKey);
            }
        }
    }

