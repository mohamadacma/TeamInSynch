package com.nashss.se.teaminsynchservice.activity;

import com.nashss.se.teaminsynchservice.activity.requests.DeleteMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.DeleteMemberResult;
import com.nashss.se.teaminsynchservice.dynamodb.MemberDao;
import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.exceptions.MemberNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteMemberActivity {

    private final Logger log = LogManager.getLogger();
    private final MemberDao memberDao;

    /**
     * Instantiates a new DeleteMemberActivity object.
     *
     * @param memberDao MemberDao to access the members table.
     */
    @Inject
    public DeleteMemberActivity(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public DeleteMemberResult handleRequest(final DeleteMemberRequest deleteMemberRequest) {

        log.info("Received DeleteMemberRequest {}", deleteMemberRequest);
        Member member = memberDao.getMemberById(deleteMemberRequest.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + deleteMemberRequest.getMemberId());
        }

        memberDao.deleteMember(deleteMemberRequest.getMemberId());
        return DeleteMemberResult.builder()
                .withStatus("SUCCESS")
                .withMessage("member deleted successfully")
                .build();
    }
}
