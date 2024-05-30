package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.MemberModel;

public class UpdateMemberResult {
    private final MemberModel member;

    private UpdateMemberResult(MemberModel member) {
        this.member = member;
    }

    public MemberModel getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "UpdateMemberResult{" +
                "member=" + member +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MemberModel member;

        public Builder withMember(MemberModel member) {
            this.member = member;
            return this;
        }

        public UpdateMemberResult build() {
            return new UpdateMemberResult(member);
        }
    }
}
