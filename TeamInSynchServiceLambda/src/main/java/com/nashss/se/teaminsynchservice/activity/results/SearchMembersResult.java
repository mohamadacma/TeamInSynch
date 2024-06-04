package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.MemberModel;

import java.util.ArrayList;
import java.util.List;

public class SearchMembersResult {
    private final List<MemberModel> members;

    private SearchMembersResult(List<MemberModel> members) {
        this.members = members;
    }

    public List<MemberModel> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "SearchMembersResult{" +
                "members=" + members +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<MemberModel> members;

        public Builder withMembers(List<MemberModel> members) {
            this.members = new ArrayList<>(members);
            return this;
        }

        public SearchMembersResult build() {
            return new SearchMembersResult(members);
        }
    }
}
