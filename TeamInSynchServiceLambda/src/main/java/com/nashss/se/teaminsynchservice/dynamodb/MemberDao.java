package com.nashss.se.teaminsynchservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

/**
 * Accesses data for a member using {@link Member} to represent the model in DynamoDB.
 */
@Singleton
public class MemberDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a Member object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the Member table
     */
    @Inject
    public MemberDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDBMapper = dynamoDbMapper;
    }

    /**
     * Retrieves a Member by memberId.
     * <p>
     * If not found, throws MemberNotFoundException.
     *
     * @param memberId The member to look up
     * @return The corresponding Member if found
     */
    public Member getMember(String memberId) {
        Member member = dynamoDBMapper.load(Member.class, memberId);
        if (null == member) {
            throw new MemberNotFoundException(
                    String.format("couldn't find the member with memberId '%s'", memberId));
        }
        return member;
    }

    /**
     * Saves (creates or updates) the given member.
     *
     * @param member The member to save
     * @return The Member object that was saved
     */
    public Member saveMember(Member member) {
        this.dynamoDBMapper.save(member);
        return member;
    }

    /**
     * Deletes a Member from the database.
     *
     * If not found, throws MemberNotFoundException.
     *
     * @param memberId The memberId to look up
     */
    public void deleteMember(String memberId) {
        Member member = getMember(memberId);
        if (member != null) {
            dynamoDBMapper.delete(member);
        } else {
            throw new MemberNotFoundException(
                    String.format("Could not find member with ID '%s'", memberId));
        }
    }

    /**
     * Retrieves a list of Member objects from the database associated with the given teamName.
     * This method uses the TeamNameIndex global secondary index to query the members by their teamName.
     *
     * @param teamName The name of the team to retrieve members for.
     * @return A list of Member objects associated with the given teamName.
     */
    public List<Member> getMembersByTeam(String teamName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":teamName", new AttributeValue().withS(teamName));

        DynamoDBQueryExpression<Member> queryExpression = new DynamoDBQueryExpression<Member>()
                .withIndexName("TeamNameIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("teamName = :teamName")
                .withExpressionAttributeValues(valueMap);

        return dynamoDBMapper.query(Member.class, queryExpression);
    }

    /**
     * Retrieves a list of Member objects from the database given a list of member IDs.
     * <p>
     * This method iterates over the provided list of member IDs, retrieves each corresponding
     * Member object using the getMember method, and adds them to a list which is then returned.
     *
     * @param memberIds A list of member IDs to look up.
     * @return A list of Member objects corresponding to the provided member IDs.
     * @throws MemberNotFoundException if any of the member IDs do not correspond to an existing member.
     */
    public Set<Member> getMembersByIds(Set<String> memberIds) {
        Set<Member> members = new HashSet<>();
        for (String memberId : memberIds) {
            members.add(getMember(memberId));
        }
        return members;
    }

    /**
     * Perform a search of the member table for members matching the given criteria.
     * Searches are CASE SENSITIVE.
     * @param memberName memberName of the members to find.
     * @param city       city of the members to find.
     * @param teamName   teamName of the members to find.
     * @return a List of member objects that match the given criteria.
     */
    public List<Member> searchMembers(String memberName, String city, String teamName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        StringBuilder keyConditionExpression = new StringBuilder();

        if (memberName != null && !memberName.isEmpty()) {
            valueMap.put(":memberName", new AttributeValue().withS(memberName));
            keyConditionExpression.append("memberName = :memberName");
        }

        if (city != null && !city.isEmpty()) {
            if (keyConditionExpression.length() > 0) keyConditionExpression.append(" AND ");
            valueMap.put(":city", new AttributeValue().withS(city));
            keyConditionExpression.append("city = :city");
        }

        if (teamName != null && !teamName.isEmpty()) {
            if (keyConditionExpression.length() > 0) keyConditionExpression.append(" AND ");
            valueMap.put(":teamName", new AttributeValue().withS(teamName));
            keyConditionExpression.append("teamName = :teamName");
        }

        DynamoDBQueryExpression<Member> queryExpression = new DynamoDBQueryExpression<Member>()
                .withConsistentRead(false)
                .withKeyConditionExpression(keyConditionExpression.toString())
                .withExpressionAttributeValues(valueMap);

        // Determine which index to use based on the search criteria
        if (memberName != null && !memberName.isEmpty()) {
            queryExpression.withIndexName("MemberNameIndex");
        } else if (city != null && !city.isEmpty()) {
            queryExpression.withIndexName("CityIndex");
        } else if (teamName != null && !teamName.isEmpty()) {
            queryExpression.withIndexName("TeamNameIndex");
        }

        return dynamoDBMapper.query(Member.class, queryExpression);
    }
}


