package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.AddMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.AddMemberResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the AddMemberActivity for the TeamInSynchService's AddMember API.
 * <p>
 * This API allows the customer to create a new member.
 */

public class AddMemberActivity {
    private final Logger log = LogManager.getLogger();
    private final MemberDao memberDao;

    /**
     * Instantiates a new AddMemberActivity object.
     *
     * @param memberDao MemberDao to access the members table.
     */
    @Inject
    public AddMemberActivity(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * This method handles the incoming request by persisting a new member
     * with the provided member details such as member ID, member name, join date,
     * phone number, city, background, role, and member email from the request.
     * <p>
     * It then returns the newly created member.
     * <p>
     * If the provided member name, phone number, city, background, role, or member email
     * has invalid characters, throws an InvalidAttributeValueException.
     *
     * @param addMemberRequest request object containing the member details
     * @return addMemberResult result object containing the API defined {@link MemberModel}
     */
    public AddMemberResult handleRequest(final AddMemberRequest addMemberRequest) {
        log.info("Received AddMemberRequest {}", addMemberRequest);
        if(!TeamInSynchServiceUtils.)

    }
}
