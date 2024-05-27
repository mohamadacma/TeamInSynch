package com.nashss.se.teaminsynchservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.teaminsynchservice.dynamodb.models.Team;
import com.nashss.se.teaminsynchservice.exceptions.TeamNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Accesses data for a team using {@link Team} to represent the model in DynamoDB.
 */
@Singleton
public class TeamDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a Member object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the Member table
     */
    @Inject
    public TeamDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDBMapper = dynamoDbMapper;
    }

    /**
     * Retrieves a Team by email.
     * <p>
     * If not found, throws TeamNotFoundException.
     *
     * @param email team to look up
     * @return The corresponding Team if found
     */

    public Team getTeam(String email, String teamName) {
        Team team = dynamoDBMapper.load(Team.class, email, teamName);
        if (null == team) {
            throw new TeamNotFoundException(
                    String.format("couldn't find the team associated with the email '%s' and teamName '%s'", email, teamName));
        }
        return team;
    }

    /**
     * Retrieves all teams for a specific manager (by email).
     *
     * @param email the email of the manager
     * @return A list of teams managed by the given email
     */
    public List<Team> getAllTeamsByManager(String email) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":email", new AttributeValue().withS(email));

        DynamoDBQueryExpression<Team> queryExpression = new DynamoDBQueryExpression<Team>()
                .withKeyConditionExpression("email = :email")
                .withExpressionAttributeValues(valueMap);

        return dynamoDBMapper.query(Team.class, queryExpression);
    }
}
