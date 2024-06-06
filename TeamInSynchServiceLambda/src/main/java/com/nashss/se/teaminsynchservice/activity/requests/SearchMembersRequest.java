package com.nashss.se.teaminsynchservice.activity.requests;

public class SearchMembersRequest {
    private final String memberName;
    private final String city;
    private final String teamName;
    private final String managerEmail;

    private SearchMembersRequest(String memberName, String city, String teamName, String managerEmail) {
        this.memberName = memberName;
        this.city = city;
        this.teamName = teamName;
        this.managerEmail = managerEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getCity() {
        return city;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    @Override
    public String toString() {
        return "SearchMembersRequest{" +
                "memberName='" + memberName + '\'' +
                ", city='" + city + '\'' +
                ", teamName='" + teamName + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String memberName;
        private String city;
        private String teamName;
        private String managerEmail;

        public Builder withMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder withManagerEmail(String managerEmail) {
            this.managerEmail = managerEmail;
            return this;
        }

        public SearchMembersRequest build() {
            return new SearchMembersRequest(memberName, city, teamName, managerEmail);
        }
    }
}
