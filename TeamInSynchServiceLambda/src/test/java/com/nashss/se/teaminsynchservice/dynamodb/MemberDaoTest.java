package com.nashss.se.teaminsynchservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class MemberDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private MemberDao memberDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        memberDao = new MemberDao(dynamoDBMapper);
    }

    @Test
    public void getMember_withMemberId_callsMapperWithPartitionKey() {
        //GIVEN
        String memberId = "testId";
        Member member = new Member();
        member.setMemberId(memberId);
        when(dynamoDBMapper.load(Member.class,memberId)).thenReturn(member);
        //WHEN
        Member result = memberDao.getMemberById(memberId);
        //THEN
        Assertions.assertNotNull(result);
        verify(dynamoDBMapper).load(Member.class,memberId);
    }

    @Test
    public void getMember_withInvalidMemberId_throwsMemberNotFoundException() {
        // GIVEN
        String invalidMemberId = "invalidId";

        when(dynamoDBMapper.load(Member.class, invalidMemberId)).thenReturn(null);

        // WHEN & THEN
        Assertions.assertThrows(MemberNotFoundException.class, () -> {
            memberDao.getMemberById(invalidMemberId);
        });

        verify(dynamoDBMapper).load(Member.class, invalidMemberId);
    }
    }



