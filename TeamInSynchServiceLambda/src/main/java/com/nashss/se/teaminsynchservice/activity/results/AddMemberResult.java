package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.MemberModel;

public class AddMemberResult {
    private MemberModel member;

    private AddMemberResult(MemberModel member) {
        this.member = member;
    }

    public MemberModel getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "AddMemberResult{" +
                "member=" + member +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MemberModel member;

        public Builder withMember(MemberModel member) {
            this.member = member;
            return this;
        }

        public AddMemberResult build() {
            return new AddMemberResult(member);
        }
    }
}
