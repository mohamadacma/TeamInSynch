package com.nashss.se.teaminsynchservice.models;

import com.nashss.se.teaminsynchservice.dynamodb.models.Member;

import java.time.ZonedDateTime;
import java.util.Objects;

public class MemberModel {
    private final String memberId;
    private final String name;
    private final String memberEmail;
    private final String phoneNumber;
    private final String city;
    private final ZonedDateTime joinDate;

    public MemberModel(String memberId, String name, String memberEmail, String phoneNumber, String city, ZonedDateTime joinDate) {
        this.memberId = memberId;
        this.name = name;
        this.memberEmail = memberEmail;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.joinDate = joinDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
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

    public ZonedDateTime getJoinDate() {
        return joinDate;
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
                Objects.equals(name, that.name) &&
                Objects.equals(memberEmail, that.memberEmail) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(city, that.city) &&
                Objects.equals(joinDate, that.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name, memberEmail, phoneNumber, city, joinDate);
    }

    public static class Builder {
        private String memberId;
        private String name;
        private String memberEmail;
        private String phoneNumber;
        private String city;
        private ZonedDateTime joinDate;

        public Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
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

        public Builder withJoinDate(ZonedDateTime joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        public MemberModel build() {
            return new MemberModel(memberId, memberEmail, name, city, phoneNumber, joinDate);
        }
    }
}
