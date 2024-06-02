package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.UpdateMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.UpdateMemberResult;
import com.nashss.se.teaminsynchservice.converters.ModelConverter;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import com.nashss.se.teaminsynchservice.utils.TeamInSynchServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdateMemberActivity for the TeamInSynchService's UpdateMember API.
 *
 * This API allows the customer to update their saved member's information.
 */

public class UpdateMemberActivity {
        private final Logger log = LogManager.getLogger();
        private final MemberDao memberDao;

    /**
     * Instantiates a new UpdateMemberActivity object.
     *
     * @param memberDao PlaylistDao to access the playlist table.
     */
    @Inject
    public UpdateMemberActivity(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * This method handles the incoming request by retrieving the member, updating it,
     * and persisting the member.
     * <p>
     * It then returns the updated member.
     * <p>
     * If the member does not exist, this should throw a MemberNotFoundException.
     * <p>
     * If the provided member details have invalid characters, throws an
     * InvalidAttributeValueException.
     *
     * @param updateMemberRequest request object containing the member ID and updated member details.
     * @return updateMemberResult result object containing the API defined {@link MemberModel}
     */
    public UpdateMemberResult handleRequest(final UpdateMemberRequest updateMemberRequest) {
        log.info("Received UpdateMemberRequest {}", updateMemberRequest);

        // Retrieve member
        Member member = memberDao.getMemberById(updateMemberRequest.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + updateMemberRequest.getMemberId());
        }

        // Validate fields
            if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getMemberName())) {
                throw new InvalidAttributeValueException("Member name [" + updateMemberRequest.getMemberName() +
                        "] contains illegal characters");
        }
                if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getPhoneNumber())) {
                    throw new InvalidAttributeValueException("Phone number [" + updateMemberRequest.getPhoneNumber() +
                            "] contains illegal characters");
                }
                    if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getCity())) {
                        throw new InvalidAttributeValueException("City [" + updateMemberRequest.getCity() +
                                "] contains illegal characters");
                    }
                    if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getBackground())) {
                        throw new InvalidAttributeValueException("Background [" + updateMemberRequest.getBackground() +
                                "] contains illegal characters");
                    }

                    if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getRole())) {
                        throw new InvalidAttributeValueException("Role [" + updateMemberRequest.getRole() +
                                "] contains illegal characters");
                    }

                    if (!TeamInSynchServiceUtils.isValidString(updateMemberRequest.getMemberEmail())) {
                        throw new InvalidAttributeValueException("Email [" + updateMemberRequest.getMemberEmail() +
                                "] contains illegal characters");
                    }
                    member.setMemberName(updateMemberRequest.getMemberName());
                    member.setPhoneNumber(updateMemberRequest.getPhoneNumber());
                    member.setCity(updateMemberRequest.getCity());
                    member.setBackground(updateMemberRequest.getBackground());
                    member.setRole(updateMemberRequest.getRole());
                    member.setMemberEmail(updateMemberRequest.getMemberEmail());
                    memberDao.saveMember(member);

                    return UpdateMemberResult.builder()
                            .withMember(new ModelConverter().toMemberModel(member))
                            .build();
                }
}




