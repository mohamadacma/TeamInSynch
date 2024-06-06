package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.SearchMembersRequest;
import com.nashss.se.teaminsynchservice.activity.results.SearchMembersResult;
import com.nashss.se.teaminsynchservice.converters.ModelConverter;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the SearchMembersActivity for the TeamInSYnchService's  SearchMembers API.
 * <p>
 * This API allows the customer to search for members by teamName, city, or memberName.
 */
public class SearchMembersActivity {
    private final Logger log = LogManager.getLogger();
    private final MemberDao memberDao;
    /**
     * Instantiates a new SearchMembersActivity object.
     *
     * @param memberDao MemberDao to access the members table.
     */
    @Inject
    public SearchMembersActivity(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * This method handles the incoming request by searching for members from the database.
     * <p>
     * It then returns the matching members, or an empty result list if none are found.
     *
     * @param searchMembersRequest request object containing the search criteria
     * @return searchMembersResult result object containing the members that match the
     * search criteria.
     */
    public SearchMembersResult handleRequest(final SearchMembersRequest searchMembersRequest) {
        log.info("Received SearchMembersRequest {}", searchMembersRequest);

        if ((searchMembersRequest.getMemberName() == null || searchMembersRequest.getMemberName().isEmpty()) &&
                (searchMembersRequest.getCity() == null || searchMembersRequest.getCity().isEmpty()) &&
                (searchMembersRequest.getTeamName() == null || searchMembersRequest.getTeamName().isEmpty())) {
            throw new IllegalArgumentException("At least one search criteria (memberName, city, teamName) must be provided");
        }

            List<Member> results = memberDao.searchMembers(searchMembersRequest.getMemberName(),searchMembersRequest.getCity(),searchMembersRequest.getTeamName());
            List<MemberModel> memberModels = new ModelConverter().toMemberModelList(results);

            return SearchMembersResult.builder()
                    .withMembers(memberModels)
                    .build();
        }
}

