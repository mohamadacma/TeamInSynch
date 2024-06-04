package com.nashss.se.teaminsynchservice.activity.requests;


public class SearchMembersRequest {
    private final String memberName;
    private final String city;
    private final String teamName;

    private SearchMembersRequest(String memberName, String city, String teamName) {
        this.memberName = memberName;
        this.city = city;
        this.teamName = teamName;
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

    @Override
    public String toString() {
        return "SearchMembersRequest{" +
                "memberName='" + memberName + '\'' +
                ", city='" + city + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String memberName;
        private String city;
        private String teamName;

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

        public SearchMembersRequest build() {
            return new SearchMembersRequest(memberName, city, teamName);
        }
    }
}
