package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.GetMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.AddMemberResult;
import com.nashss.se.teaminsynchservice.activity.results.GetMemberResult;
import com.nashss.se.teaminsynchservice.converters.ModelConverter;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Implementation of the GetMemberActivity for the TeamInSynchService's GetMember API.
 * <p>
 * This API allows the customer to get member details.
 */
public class GetMemberActivity {
    private final Logger log = LogManager.getLogger();
    private final MemberDao memberDao;

    /**
     * Instantiates a new GetMemberActivity object.
     *
     * @param memberDao MemberDao to access the members table.
     */
    @Inject
    public GetMemberActivity(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * This method handles the incoming request to retrieve a member's details
     * using the provided member ID.
     * <p>
     * If the member is found, it converts the member entity to the API defined {@link MemberModel}
     * and returns it wrapped in a {@link GetMemberResult}.
     * <p>
     * If the member is not found, it throws a {@link MemberNotFoundException}.
     *
     * @param getMemberRequest request object containing the member ID
     * @return getMemberResult result object containing the API defined {@link MemberModel}
     */
    public GetMemberResult handleRequest(final GetMemberRequest getMemberRequest) {
        log.info("Received getMember request {}", getMemberRequest);
        Member member = memberDao.getMemberById(getMemberRequest.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + getMemberRequest.getMemberId());
        }

        MemberModel memberModel = new ModelConverter().toMemberModel(member);
        return GetMemberResult.builder()
                .withMember(memberModel)
                .build();
    }
}


