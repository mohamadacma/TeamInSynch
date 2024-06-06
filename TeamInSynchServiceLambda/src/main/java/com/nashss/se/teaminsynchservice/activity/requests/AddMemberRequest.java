package com.nashss.se.teaminsynchservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddMemberRequest.Builder.class)
public class AddMemberRequest {
    private final String memberName;
    private final String joinDate;
    private final String phoneNumber;
    private final String city;
    private final String background;
    private final String role;
    private final String memberEmail;
    private final String managerEmail;
    private final String teamName;

    private AddMemberRequest(String memberName, String joinDate, String phoneNumber, String city, String background, String role, String memberEmail, String managerEmail, String teamName) {
        this.memberName = memberName;
        this.joinDate = joinDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.background = background;
        this.role = role;
        this.memberEmail = memberEmail;
        this. managerEmail = managerEmail;
        this.teamName= teamName;

    }

    public String getMemberName() {
        return memberName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getBackground() {
        return background;
    }

    public String getRole() {
        return role;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return "AddMemberRequest{" +
                ", memberName='" + memberName + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", background='" + background + '\'' +
                ", role='" + role + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String memberName;
        private String joinDate;
        private String phoneNumber;
        private String city;
        private String background;
        private String role;
        private String memberEmail;
        private String managerEmail;
        private String teamName;

        public Builder withMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public Builder withJoinDate(String joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withBackground(String background) {
            this.background = background;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public Builder withMemberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
            return this;
        }

        public Builder withManagerEmail(String managerEmail) {
            this.managerEmail = managerEmail;
            return this;
        }

        public Builder withTeamName(String teamName) {
            this.managerEmail = managerEmail;
            return this;
        }


        public AddMemberRequest build() {
            return new AddMemberRequest( memberName, joinDate, phoneNumber, city, background, role, memberEmail, managerEmail,teamName);
        }
    }
}
