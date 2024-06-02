package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.DeleteMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.DeleteMemberResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteMemberActivityTest {
    @Mock
    MemberDao memberDao;

    private DeleteMemberActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new DeleteMemberActivity(memberDao);
    }

    @Test
    public void handleRequest_withValidId_deletesMember() {
        // GIVEN
        String memberId = "123";
        String memberName = "John Wick";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "Beirut";
        String background = "Juggler";
        String role = "Coffee maker";
        String memberEmail = "john.wick@example.com";

        Member existingMember = new Member();
        existingMember.setMemberId(memberId);
        existingMember.setMemberName(memberName);
        existingMember.setJoinDate(joinDate);
        existingMember.setPhoneNumber(phoneNumber);
        existingMember.setCity(city);
        existingMember.setBackground(background);
        existingMember.setRole(role);
        existingMember.setMemberEmail(memberEmail);
        existingMember.setMemberId(memberId);

        when(memberDao.getMemberById(memberId)).thenReturn(existingMember);

        DeleteMemberRequest request = DeleteMemberRequest.builder()
                .withMemberId(memberId)
                .build();
        //WHEN
        DeleteMemberResult result = activity.handleRequest(request);
        //THEN
        verify(memberDao).deleteMember(memberId);
        Assertions.assertNotNull(result.getMessage());
        Assertions.assertNotNull(result.getStatus());
    }

    @Test
    public void handleRequest_withInvalidId_throwMemberNotFoundException() {
        //GIVEN
        String invalidId = "invalidId";
        when(memberDao.getMemberById(invalidId)).thenReturn(null);

            DeleteMemberRequest invalidRequest = DeleteMemberRequest.builder()
                    .withMemberId(invalidId)
                    .build();
            //WHEN AND THEN
        assertThrows(MemberNotFoundException.class, () -> {
            activity.handleRequest(invalidRequest);
        });
        }
    }


