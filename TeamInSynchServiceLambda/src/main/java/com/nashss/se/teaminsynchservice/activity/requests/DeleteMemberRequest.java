package com.nashss.se.teaminsynchservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddMemberRequest.Builder.class)
public class DeleteMemberRequest {
    private final String memberId;

    public DeleteMemberRequest(String memberId) {
        this.memberId = memberId;
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

        public Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public DeleteMemberRequest build() {
            return new DeleteMemberRequest(memberId);
        }
    }
}
