package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.AddMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.AddMemberResult;
import com.nashss.se.teaminsynchservice.converters.ModelConverter;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import com.nashss.se.teaminsynchservice.utils.TeamInSynchServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

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
        // Parse join date to ISO 8601 format
        String isoJoinDate = formatToIsoDate(addMemberRequest.getJoinDate());

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getMemberName())) {
            throw new InvalidAttributeValueException("Member name [" + addMemberRequest.getMemberName() +
                    "] contains illegal characters");
        }

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getMemberEmail())) {
            throw new InvalidAttributeValueException("Member email [" + addMemberRequest.getMemberEmail() +
                    "] contains illegal characters");
        }

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getPhoneNumber())) {
            throw new InvalidAttributeValueException("Phone number [" + addMemberRequest.getPhoneNumber() +
                    "] contains illegal characters");
        }

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getCity())) {
            throw new InvalidAttributeValueException("City [" + addMemberRequest.getCity() +
                    "] contains illegal characters");
        }

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getBackground())) {
            throw new InvalidAttributeValueException("Background [" + addMemberRequest.getBackground() +
                    "] contains illegal characters");
        }

        if (!TeamInSynchServiceUtils.isValidString(addMemberRequest.getRole())) {
            throw new InvalidAttributeValueException("Role [" + addMemberRequest.getRole() +
                    "] contains illegal characters");
        }

            Member newMember = new Member();
            newMember.setMemberId(TeamInSynchServiceUtils.generateMemberId());
            newMember.setMemberName(addMemberRequest.getMemberName());
            newMember.setMemberEmail(addMemberRequest.getMemberEmail());
            newMember.setPhoneNumber(addMemberRequest.getPhoneNumber());
            newMember.setCity(addMemberRequest.getCity());
            newMember.setJoinDate(isoJoinDate);
            newMember.setBackground(addMemberRequest.getBackground());
            newMember.setRole(addMemberRequest.getRole());

            memberDao.saveMember(newMember);

            MemberModel memberModel = new ModelConverter().toMemberModel(newMember);
            return AddMemberResult.builder()
                    .withMember(memberModel)
                    .build();
        }
    private String formatToIsoDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime dateTime = parsedDate.atStartOfDay();
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return dateTime.atOffset(ZoneOffset.UTC).format(isoFormatter);
        } catch (Exception e) {
            throw new InvalidAttributeValueException("Join date [" + date + "] is not in valid format. Expected format: yyyy-MM-dd");
        }
    }
}

