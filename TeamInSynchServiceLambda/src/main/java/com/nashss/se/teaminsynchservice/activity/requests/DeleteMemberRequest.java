package com.nashss.se.teaminsynchservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddMemberRequest.Builder.class)
public class DeleteMemberRequest {
    private final String memberId;
    private final String managerEmail;

    public DeleteMemberRequest(String memberId, String managerEmail) {

        this.memberId = memberId;
        this.managerEmail = managerEmail;
    }
    public String getMemberId() {
        return memberId;
    }
    public String getManagerEmail() { return managerEmail;}
    @Override
    public String toString() {
        return "DeleteMemberRequest{" +
                "memberId='" + memberId + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String memberId;
        private String managerEmail;

        public Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }
        public Builder withManagerEmail(String managerEmail) {
            this.managerEmail = managerEmail;
            return this;
        }

        public DeleteMemberRequest build() {
            return new DeleteMemberRequest(memberId, managerEmail);
        }
    }
}
