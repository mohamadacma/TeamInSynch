package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.GetMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetMemberResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetMemberActivityTest {
    @Mock
    MemberDao memberDao;

    private GetMemberActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new GetMemberActivity(memberDao);
    }
    @Test
    public void handleRequest_withMemberId_returnMemberDetails() {
        String memberId = "123";
        String memberName = "Ip Man";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "Sao Paulo";
        String background = "Juggler";
        String role = "Tea maker";
        String memberEmail = "john.Claude@example.com";

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

        // setup mock behavior
        when(memberDao.getMemberById(memberId)).thenReturn(existingMember);

        GetMemberRequest validRequest = GetMemberRequest.builder()
                .withMemberId(memberId)
                .build();
        //WHEN
        GetMemberResult result = activity.handleRequest(validRequest);
        //THEN
        verify(memberDao,times(1)).getMemberById(memberId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(role, result.getMember().getRole());
    }
    @Test
    public void handleRequest_withNullId_throwMemberNotFoundException(){
        //GIVEN
        String invalidId = "invalidId";
        when(memberDao.getMemberById(invalidId)).thenReturn(null);

        GetMemberRequest invalidRequest = GetMemberRequest.builder()
                .withMemberId(invalidId)
                .build();
        //WHEN AND THEN
        assertThrows(MemberNotFoundException.class, () -> {
            activity.handleRequest(invalidRequest);
        });
    }
}