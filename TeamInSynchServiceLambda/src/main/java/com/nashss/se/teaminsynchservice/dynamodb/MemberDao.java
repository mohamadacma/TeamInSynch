package com.nashss.se.teaminsynchservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Perform a search (via a "scan") of the member table for members matching the given city and teamName.
     * Searches are CASE SENSITIVE.
     *
     * @param city an city of the members to find
     * @param teamName teamName of the members to find
     * @return a List of member objects that match the given city and teamName
     */

    public List<Member> searchMembers( String city, String teamName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        StringBuilder filterExpression = new StringBuilder();

            if (city != null && !city.isEmpty()) {
                if (filterExpression.length() > 0) filterExpression.append(" AND ");
                valueMap.put(":city", new AttributeValue().withS(city));
                filterExpression.append("city = :city");
            }

            if (teamName != null && !teamName.isEmpty()) {
                if (filterExpression.length() > 0) filterExpression.append(" AND ");
                valueMap.put(":teamName", new AttributeValue().withS(teamName));
                filterExpression.append("teamName = :teamName");
            }

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(filterExpression.toString())
                    .withExpressionAttributeValues(valueMap);

            return dynamoDBMapper.scan(Member.class, scanExpression);
        }
    }


