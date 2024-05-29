package com.nashss.se.teaminsynchservice.models;

import com.nashss.se.teaminsynchservice.dynamodb.models.Member;

import java.time.ZonedDateTime;
import java.util.Objects;

public class MemberModel {
    private final String memberId;
    private final String memberName;
    private final String memberEmail;
    private final String phoneNumber;
    private final String city;
    private final String joinDate;
    private final String background;
    private final String role;

    public MemberModel(String memberId, String memberName, String memberEmail, String phoneNumber, String city, String joinDate, String background, String role) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.joinDate = joinDate;
        this.background = background;
        this.role = role;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getBackground() {
        return background;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberModel that = (MemberModel) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(memberEmail, that.memberEmail) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(city, that.city) &&
                Objects.equals(joinDate, that.joinDate) &&
                Objects.equals(background, that.background) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, memberName, memberEmail, phoneNumber, city, joinDate, background, role);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String memberId;
        private String memberName;
        private String memberEmail;
        private String phoneNumber;
        private String city;
        private String joinDate;
        private String background;
        private String role;

        public Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder withMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public Builder withMemberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
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

        public Builder withJoinDate(String joinDate) {
            this.joinDate = joinDate;
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

        public MemberModel build() {
            return new MemberModel(memberId, memberName, memberEmail, phoneNumber, city, joinDate, background, role);
        }
    }
}
