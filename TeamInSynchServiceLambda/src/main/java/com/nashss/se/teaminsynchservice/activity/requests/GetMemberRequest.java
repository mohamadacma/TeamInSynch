package com.nashss.se.teaminsynchservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddMemberRequest.Builder.class)
public class GetMemberRequest {
        private final String memberId;
        private final String managerEmail;

        public GetMemberRequest(String memberId, String managerEmail) {

            this.memberId = memberId;
            this.managerEmail = managerEmail;
        }
        public String getMemberId() {
            return memberId;
        }
        @Override
        public String toString() {
            return "DeleteMemberRequest{" +
                    "memberId='" + memberId + '\'' +
                    '}';
        }
        public static Builder builder() {
            return new Builder();
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


            public GetMemberRequest build() {
                return new GetMemberRequest(memberId, managerEmail);
            }
        }
    }

