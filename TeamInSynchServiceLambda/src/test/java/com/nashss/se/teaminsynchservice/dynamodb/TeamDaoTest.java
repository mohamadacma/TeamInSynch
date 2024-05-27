package com.nashss.se.teaminsynchservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.teaminsynchservice.dynamodb.models.Team;
import com.nashss.se.teaminsynchservice.exceptions.TeamNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeamDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private TeamDao teamDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        teamDao = new TeamDao(dynamoDBMapper);
    }

    @Test
    public void getTeam_withEmailAndTeamName_returnsListOfTeams() {
        //GIVEN
        String testEmail = "testEmail@test.com";
        String testTeamName= "testTeamName";
        Team team = new Team();
        team.setEmail(testEmail);
        team.setTeamName(testTeamName);
        when(dynamoDBMapper.load(Team.class, testEmail, testTeamName)).thenReturn(team);
        //WHEN
        Team result = teamDao.getTeam(testEmail,testTeamName);
        //THEN
        verify(dynamoDBMapper).load(Team.class, testEmail,testTeamName);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(testEmail,result.getEmail());
        Assertions.assertEquals(testTeamName,result.getTeamName());
    }

    @Test
    public void getTeam_withInvalidEmailAndTeamName_throwsTeamNotFoundException(){
        // GIVEN
        String invalidEmail = "invalidEmail@test.com";
        String invalidTeamName = "invalidTeamName";
        when(dynamoDBMapper.load(Team.class,invalidEmail,invalidTeamName)).thenReturn(null);
        // WHEN & THEN
        Assertions.assertThrows(TeamNotFoundException.class, () -> {
            teamDao.getTeam(invalidEmail, invalidTeamName);
        });
        verify(dynamoDBMapper).load(Team.class, invalidEmail, invalidTeamName);
    }
    }
