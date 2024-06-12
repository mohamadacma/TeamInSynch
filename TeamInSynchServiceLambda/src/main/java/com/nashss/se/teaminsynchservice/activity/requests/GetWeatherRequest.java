package com.nashss.se.teaminsynchservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

    @JsonDeserialize(builder = GetWeatherRequest.Builder.class)
    public class GetWeatherRequest {
        private final String memberId;
        private final String managerEmail;

        public GetWeatherRequest(String memberId, String managerEmail) {

            this.memberId = memberId;
            this.managerEmail = managerEmail;
        }
        public String getMemberId() {
            return memberId;
        }
        public String getManagerEmail() {
            return managerEmail;
        }
        @Override
        public String toString() {
            return "FetchWeatherRequest{" +
                    "memberId='" + memberId + '\'' +
                    ", managerEmail='" + managerEmail + '\'' +
                    '}';
        }
        public static Builder builder() {
            return new GetWeatherRequest.Builder();
        }
        @JsonPOJOBuilder
        public static class Builder {
            private String memberId;
            private String managerEmail;

            public Builder withMemberId (String memberId) {
                this.memberId = memberId;
                return this;
            }

            public Builder withManagerEmail (String managerEmail) {
                this.managerEmail = managerEmail;
                return this;
            }

            public GetWeatherRequest build() {
                return new GetWeatherRequest(memberId, managerEmail);
            }
        }
    }
