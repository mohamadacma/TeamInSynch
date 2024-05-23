package com.nashss.se.teaminsynchservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "Teams")
public class Team {
    private String email;
    private String teamName;
    private Set<String> memberIds;

    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBRangeKey(attributeName = "teamName")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @DynamoDBAttribute(attributeName = "memberIds")
    public Set<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<String> memberIds) {
        this.memberIds = memberIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team that = (Team) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(memberIds, that.memberIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, teamName, memberIds);
    }

    @Override
    public String toString() {
        return "Team{" +
                "email='" + email + '\'' +
                ", teamName='" + teamName + '\'' +
                ", memberIds=" + memberIds +
                '}';
    }

}
