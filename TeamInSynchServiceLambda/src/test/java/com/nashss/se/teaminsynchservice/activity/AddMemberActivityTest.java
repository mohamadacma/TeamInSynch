package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.AddMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.AddMemberResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddMemberActivityTest {

    @Mock
    private MemberDao memberDao;

    private AddMemberActivity addMemberActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addMemberActivity = new AddMemberActivity(memberDao);
    }

    @Test
    public void handleRequest_withValidInputs_createsAndSavesMemberToDB(){
        // GIVEN
        String memberId = "12345";
        String memberName = "Rachel Adams";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "California";
        String background = "testBackground";
        String role = "thirdWheel";
        String memberEmail = "rachel@example.com";

        AddMemberRequest testRequest = AddMemberRequest.builder()
                .withMemberId(memberId)
                .withMemberName(memberName)
                .withJoinDate(joinDate)
                .withPhoneNumber(phoneNumber)
                .withCity(city)
                .withBackground(background)
                .withRole(role)
                .withMemberEmail(memberEmail)
                .build();
        //WHEN
        AddMemberResult testResult = addMemberActivity.handleRequest(testRequest);
        //THEN
        verify(memberDao).saveMember(any(Member.class));

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(memberName,testResult.getMember().getName());
        Assertions.assertEquals(city,testResult.getMember().getCity());
    }

    @Test
    public void handleRequest_withInvalidCity_throwInvalidAttributeValueException(){
        // GIVEN
        String memberId = "12345";
        String memberName = "Rachel Adams";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "New \"illegal\"york123!@#";
        String background = "comedian";
        String role = "being funny";
        String memberEmail = "rachel@example.com";

        AddMemberRequest invalidRequest = AddMemberRequest.builder()
                .withMemberId(memberId)
                .withMemberName(memberName)
                .withJoinDate(joinDate)
                .withPhoneNumber(phoneNumber)
                .withCity(city)
                .withBackground(background)
                .withRole(role)
                .withMemberEmail(memberEmail)
                .build();
        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> addMemberActivity.handleRequest(invalidRequest));
    }
    }

