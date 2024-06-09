package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.UpdateMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.UpdateMemberResult;
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

public class UpdateMemberActivityTest {
    @Mock
    private MemberDao memberDao;

    private UpdateMemberActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new UpdateMemberActivity(memberDao);
    }
    @Test
    public void handleRequest_updateName_returnMemberWithUpdatedName() {
        // GIVEN
        String memberId = "123";
        String originalName = "John Wick";
        String updatedName = "Emma Roberts";
        String joinDate = "2024-05-29T00:00:00.000Z";
        String phoneNumber = "123-456-7890";
        String city = "Beirut";
        String background = "Juggler";
        String role = "Coffee maker";
        String memberEmail = "john.wick@example.com";

        Member existingMember = new Member();
        existingMember.setMemberId(memberId);
        existingMember.setMemberName(originalName);
        existingMember.setJoinDate(joinDate);
        existingMember.setPhoneNumber(phoneNumber);
        existingMember.setCity(city);
        existingMember.setBackground(background);
        existingMember.setRole(role);
        existingMember.setMemberEmail(memberEmail);
        existingMember.setTeamName("alpha");

        when(memberDao.getMemberById(memberId)).thenReturn(existingMember);

        UpdateMemberRequest request = UpdateMemberRequest.builder()
                .withMemberId(memberId)
                .withMemberName(updatedName)
                .withJoinDate(joinDate)
                .withPhoneNumber(phoneNumber)
                .withCity(city)
                .withBackground(background)
                .withRole(role)
                .withMemberEmail(memberEmail)
                .withTeamName("alpha")
                .build();
        //WHEN
        UpdateMemberResult result = activity.handleRequest(request);
        //THEN
        verify(memberDao).saveMember(existingMember);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedName, result.getMember().getMemberName());
        Assertions.assertEquals(phoneNumber, result.getMember().getPhoneNumber());
        Assertions.assertEquals(city, result.getMember().getCity());
        Assertions.assertEquals(background, result.getMember().getBackground());
        Assertions.assertEquals(role, result.getMember().getRole());
        Assertions.assertEquals(memberEmail, result.getMember().getMemberEmail());
    }

    @Test
    public void handleRequest_withInvalidId_throwMemberNotFoundException() {
        //GIVEN
        String invalidId= "123!";
        when(memberDao.getMemberById(invalidId)).thenReturn(null);

        UpdateMemberRequest request = UpdateMemberRequest.builder()
                .withMemberId(invalidId)
                .build();
        // WHEN & THEN
        assertThrows(MemberNotFoundException.class, () -> {
            activity.handleRequest(request);
        });
    }

    }

