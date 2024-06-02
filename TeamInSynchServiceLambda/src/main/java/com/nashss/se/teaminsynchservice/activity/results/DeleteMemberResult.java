package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.MemberModel;

public class DeleteMemberResult {
    private final String status;
    private final String message;

    private DeleteMemberResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UpdateMemberResult{" +
                "message=" + message +
                "status" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String status;
        private String message;

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }
        public Builder withMessage(String message) {
            this.message= message;
            return this;
        }

        public DeleteMemberResult build() {
            return new DeleteMemberResult(status,message);
        }
    }
}
