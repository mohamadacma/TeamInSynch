package com.nashss.se.teaminsynchservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

/**
 * Represents a record in the members table.
 */
@DynamoDBTable(tableName = "Members")
public class Member {
    private String memberId;
    private String memberName;
    private String joinDate;  // store in ISO 8601 format(YYYY-MM-DDThh:mm:ss.sssZ)
    private String phoneNumber;
    private String city;
    private String background;
    private String role;
    private String memberEmail;
    private String teamName;  // Add this attribute

    @DynamoDBHashKey(attributeName = "memberId")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @DynamoDBAttribute(attributeName = "memberName")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @DynamoDBAttribute(attributeName = "joinDate")
    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBAttribute(attributeName = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @DynamoDBAttribute(attributeName = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @DynamoDBAttribute(attributeName = "background")
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @DynamoDBAttribute(attributeName = "memberEmail")
    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    @DynamoDBAttribute(attributeName = "teamName")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member that = (Member) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(joinDate, that.joinDate) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(city, that.city) &&
                Objects.equals(role, that.role) &&
                Objects.equals(background, that.background) &&
                Objects.equals(memberEmail, that.memberEmail) &&
                Objects.equals(teamName, that.teamName);  // Add this line
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, memberName, joinDate, phoneNumber, city, role, background, memberEmail, teamName);  // Add teamName
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", joinDate=" + joinDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", role='" + role + '\'' +
                ", background='" + background + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", teamName='" + teamName + '\'' +  // Add this line
                '}';
    }
}
