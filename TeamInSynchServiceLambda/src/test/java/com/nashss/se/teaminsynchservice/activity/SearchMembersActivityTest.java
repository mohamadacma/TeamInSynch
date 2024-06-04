package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.SearchMembersRequest;
import com.nashss.se.teaminsynchservice.activity.results.SearchMembersResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchMembersActivityTest {
    @Mock
    private MemberDao memberDao;

    private SearchMembersActivity searchMembersActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        searchMembersActivity = new SearchMembersActivity(memberDao);
    }

    @Test
    public void handleRequest_withAllParams_returnsMatchingMember() {
        //GIVEN
        String teamName = "teamName";
        String memberName = "memberName";
        String city = "city";
        Member member = new Member();
        member.setCity(city);
        member.setMemberName(memberName);
        member.setMemberId("1");
        member.setMemberEmail("email@example.com");
        member.setPhoneNumber("1234567890");
        member.setJoinDate("2021-01-01");
        member.setBackground("background");
        member.setRole("role");
        List<Member> returnedMembers = new ArrayList<>();
        returnedMembers.add(member);

        when(memberDao.searchMembers(memberName, city, teamName)).thenReturn(returnedMembers);

        SearchMembersRequest request = SearchMembersRequest.builder()
                .withMemberName(memberName)
                .withCity(city)
                .withTeamName(teamName)
                .build();
        //WHEN
        SearchMembersResult result = searchMembersActivity.handleRequest(request);
        //THEN
        List<MemberModel> expectedMembers = new ArrayList<>();
        for (Member m : returnedMembers) {
            expectedMembers.add(new MemberModel(
                    m.getMemberId(),
                    m.getMemberName(),
                    m.getMemberEmail(),
                    m.getPhoneNumber(),
                    m.getCity(),
                    m.getJoinDate(),
                    m.getBackground(),
                    m.getRole()
            ));
        }
        List<MemberModel> actualMembers = result.getMembers();
        Assertions.assertEquals(expectedMembers.size(), actualMembers.size());
        Assertions.assertEquals(expectedMembers, actualMembers);

    }

    @Test
    public void handleRequest_withNullParams_throwIllehalArgumentException() {
        String teamName = null;
        String city = null;
        String memberName = null;

        when(memberDao.searchMembers(null, null, null)).thenThrow(new IllegalArgumentException());

        SearchMembersRequest invalidRequest = SearchMembersRequest.builder()
                .withMemberName(memberName)
                .withCity(city)
                .withTeamName(teamName)
                .build();
        //WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> {
            searchMembersActivity.handleRequest(invalidRequest);
        });
    }
}
