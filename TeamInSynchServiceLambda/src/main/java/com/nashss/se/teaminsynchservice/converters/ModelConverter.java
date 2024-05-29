package com.nashss.se.teaminsynchservice.converters;

import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.MemberModel;

    /**
     * Converts between Data and API models.
     */
    public class ModelConverter {
        /**
         * Converts a provided {@link Member} into a {@link MemberModel} representation.
         *
         * @param member the member to convert
         * @return the converted member model
         */
        public MemberModel toMemberModel(Member member) {
            return MemberModel.builder()
                    .withMemberId(member.getMemberId())
                    .withMemberName(member.getMemberName())
                    .withMemberEmail(member.getMemberEmail())
                    .withPhoneNumber(member.getPhoneNumber())
                    .withCity(member.getCity())
                    .withJoinDate(member.getJoinDate())
                    .withBackground(member.getBackground())
                    .withRole(member.getRole())
                    .build();
        }
    }

